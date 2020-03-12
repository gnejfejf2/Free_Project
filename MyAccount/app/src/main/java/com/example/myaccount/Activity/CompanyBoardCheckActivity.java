package com.example.myaccount.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Adapter.CustomAdapter;
import com.example.myaccount.Adapter.ItemAdapter;
import com.example.myaccount.Adapter.PhotoAdapter;
import com.example.myaccount.Adapter.ReWritePhotoAdapter;
import com.example.myaccount.Contract.BoardComment;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.EntrepreneurContract;
import com.example.myaccount.Contract.ItemList;
import com.example.myaccount.Contract.UserData;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.Helper.EntrepreneurDBHelper;
import com.example.myaccount.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CompanyBoardCheckActivity extends AppCompatActivity {


    public TextView title_check;
    public FloatingActionButton board_commentwrite_button;
    private Button plusitem_button,delete_button,rewrite_button;
    private CustomAdapter mAdapter;
    private ItemAdapter itemAdapter;
    private ArrayList<ItemList> arrayItemList = new ArrayList<ItemList>();
    private JSONArray itemArray;
    private String boardid;

    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyboardcheck);

        title_check = findViewById(R.id.title_check);
        board_commentwrite_button = findViewById(R.id.board_commentwrite_button);
        plusitem_button = findViewById(R.id.plusitem_button);
        delete_button=findViewById(R.id.delete_button);
        rewrite_button=findViewById(R.id.rewrite_button);
        Intent intent = getIntent();
        final long mMemoId = intent.getLongExtra("id", -1);


        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String sql = "select * from ComponyBoard where _id = "+mMemoId+";";
        Cursor result = db.rawQuery(sql, null);

        result.moveToFirst();

        final String boardnumber = intent.getLongExtra("id", -1) + "";
        final String title = result.getString(1);

        final String day = result.getString(4);
        final String useuserID = result.getString(3);
        final String itemlist = result.getString(6);
        result.close();

////////////////////////////////////////////////////////////////////////
        rewrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rewriteintent=new Intent(CompanyBoardCheckActivity.this,CompanyReWriteActivity.class);
                rewriteintent.putExtra("id",mMemoId);
                startActivity(rewriteintent);
                finish();

            }
        });







/////////////////////////////////////////////////////////////////////////////////////
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db2 = EntrepreneurDBHelper.getInstance(getApplicationContext()).getWritableDatabase();
                ContentValues contentValues2 = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                contentValues2.put(EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURBOARD, "false");
                db2.update(EntrepreneurContract.EntrepreneurEntry.TABLE_NAME,contentValues2,
                        EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURID+"='"+useuserID+"'",null);






                SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.delete(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, CompanyBoardContract.ComponyBoardEntry._ID + "=" + mMemoId, null);//데이터삭제를 도와주는 db.delete
                setResult(RESULT_OK);//setResult()를 통해 돌려줄 결과를 저장
                Intent intent = new Intent(CompanyBoardCheckActivity.this, CompanyActivity.class);
                intent.putExtra("userID", useuserID);
                startActivity(intent);
                finish();
            }
        });

        ///////////////////////////////////////////////////////////////
        title_check.setText("회사명 : " + title);

        RecyclerView RecyclerView_itemlist = (RecyclerView) findViewById(R.id.RecyclerView_itemlist);
        LinearLayoutManager boardLinearLayoutManager = new LinearLayoutManager(this);
        RecyclerView_itemlist.setLayoutManager(boardLinearLayoutManager);
/////////////////////////////데이터에 저장된 json가져와서 array리스트로 변경시키고 리사이클러뷰에 연동////그리고 그 json값을 다시 넘기고 다음화면에서 array리스트로 변경

        if (itemlist != null) {
            try {
                itemArray = new JSONArray(itemlist);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//스트링을 제이슨어레이로 바꿔준후

            for (int i = 0; i < itemArray.length(); i++) {
                try {
                    JSONObject jObject = itemArray.getJSONObject(i);
                    String itemtitle = jObject.getString("title");
                    String itemcontent = jObject.getString("content");
                    String itemprice = jObject.getString("price");
                    String itemphoto = jObject.getString("photo");
                    ItemList itemList = new ItemList(itemtitle, itemcontent, itemprice, itemphoto);
                    arrayItemList.add(itemList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

////////////////////////////////////제이슨어레이를 어레이 리스트로
        }
        ////////////////////////////////////////////////////////////////////////////

        itemAdapter = new ItemAdapter(this, arrayItemList);
        RecyclerView_itemlist.setAdapter(itemAdapter);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////수정해야함////////////////////


        ////////////////////////////////클릭리스너
        itemAdapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                final int mposition = position;
                String st[] = {"1. 수정하기", "2. 삭제하기"};
                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyBoardCheckActivity.this);
                builder.setTitle("선택하십시오");
                builder.setItems(st, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent editintent = new Intent(CompanyBoardCheckActivity.this, ItemEditActivity.class);
                            editintent.putExtra("position", mposition);
                            editintent.putExtra("list", itemArray + "");
                            editintent.putExtra("boardnum", mMemoId);
                            editintent.putExtra("userID", useuserID);
                            startActivityForResult(editintent, 3);
                            finish();
                        } else if (which == 1) {
                            arrayItemList.remove(mposition);


                            JSONArray jArray2 = new JSONArray();//배열이 필요할때


                            for (int i = 0; i < arrayItemList.size(); i++) {
                                try {
                                    JSONObject secondjsonObject = new JSONObject();
                                    secondjsonObject.put("title", arrayItemList.get(i).getTitle());
                                    secondjsonObject.put("content", arrayItemList.get(i).getContent());
                                    secondjsonObject.put("price", arrayItemList.get(i).getPrice());
                                    secondjsonObject.put("photo", arrayItemList.get(i).getPhoto());

                                    jArray2.put(secondjsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            ContentValues contentValues = new ContentValues();
                            contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_ITEMLIST, jArray2.toString());
                            SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                            db.update(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, contentValues, BoardContract.BoardEntry._ID + "=" + mMemoId, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
                            setResult(RESULT_OK);
                            onActivityResult(RESULT_OK, 2, null);
                            db.close();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }


        });

////////////////////////////////////////////////////////수정해야함////////////////////
        ///////////////////////////////////////

//////////////////////////////////////////////////////댓글창
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardcomment);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ////////////////////////////////////////////////////////처음가져온제이슨어레이///////////
        final SharedPreferences prefs = getSharedPreferences("boardcomment", Activity.MODE_PRIVATE);
        final String json = prefs.getString(mMemoId + "", null);
        /////////////////////////////////////////////////////

        final ArrayList<BoardComment> mArrayList = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray jarray = new JSONArray(json);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    String comment = jObject.getString("comment");
                    String id = jObject.getString("id");
                    String commentday = jObject.getString("day");
                    BoardComment boardComment1 = new BoardComment(comment, id, commentday, boardnumber);
                    mArrayList.add(boardComment1); //첫번째 줄에 삽입됨
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mAdapter = new CustomAdapter(this, mArrayList, useuserID);

        mRecyclerView.setAdapter(mAdapter);

/////////////////////////////////////구분선주기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
//////////////////////////////////////////////////////댓글창

        plusitem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyBoardCheckActivity.this, ItemPlusActivity.class);
                intent.putExtra("boardnum", mMemoId+"");
                intent.putExtra("userID", useuserID);
                intent.putExtra("itemlist", itemlist);
                startActivity(intent);
                finish();
            }
        });


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        board_commentwrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyBoardCheckActivity.this);
                View view = LayoutInflater.from(CompanyBoardCheckActivity.this)
                        .inflate(R.layout.edit_box, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText edittext_dialog_content = (EditText) view.findViewById(R.id.edittext_dialog_content);
                final TextView edittext_dialog_id = (TextView) view.findViewById(R.id.edittext_dialog_id);
                final TextView edittext_dialog_day = (TextView) view.findViewById(R.id.edittext_dialog_day);


                ButtonSubmit.setText("삽입");

                edittext_dialog_id.setText(useuserID);
                edittext_dialog_day.setText(day);
                final AlertDialog dialog = builder.create();


                // 3. 다이얼로그에 있는 삽입 버튼을 클릭하면

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {


                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(View v) {

                        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
                        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
                        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
                        SharedPreferences prefs = getSharedPreferences("boardcomment", Activity.MODE_PRIVATE);


                        // 4. 사용자가 입력한 내용을 가져와서
                        String strComment = edittext_dialog_content.getText().toString();
                        String strID = edittext_dialog_id.getText().toString();
                        String strDay = edittext_dialog_day.getText().toString();


                        // 5. ArrayList에 추가하고

                        BoardComment boardComment = new BoardComment(strComment, strID, strDay, boardnumber);
                        mArrayList.add(boardComment); //첫번째 줄에 삽입됨
                        //mArrayList.add(dict); //마지막 줄에 삽입됨
/////////////////////////////////////////////////////배열을 제이슨어레이에 저장후 db에저장
                        SharedPreferences.Editor editor = prefs.edit();
                        JSONArray jArray = new JSONArray();//배열이 필요할때
                        try {

                            for (int i = 0; i < mArrayList.size(); i++) {
                                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                sObject.put("comment", mArrayList.get(i).getComment());
                                sObject.put("id", mArrayList.get(i).getId());
                                sObject.put("day", mArrayList.get(i).getDay());
                                sObject.put("number", mMemoId + "");
                                jArray.put(sObject);
                            }

                            Log.d("JSON Test", jArray.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editor.putString(mMemoId + "", jArray.toString());
                        editor.commit();


                        Intent intent = new Intent();

                        setResult(RESULT_OK);//리절트 ok를 셋팅
                        onActivityResult(1000, RESULT_OK, intent);//바로바로 실행시켜주기위해
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardcomment);
            mRecyclerView.setAdapter(mAdapter);

        } else if (resultCode == 2) {

            Toast.makeText(getApplicationContext(), "상품삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            RecyclerView RecyclerView_itemlist = (RecyclerView) findViewById(R.id.RecyclerView_itemlist);
            LinearLayoutManager itemLinearLayoutManager = new LinearLayoutManager(this);
            RecyclerView_itemlist.setLayoutManager(itemLinearLayoutManager);


        } else if (requestCode == 3) {
            RecyclerView RecyclerView_itemlist = (RecyclerView) findViewById(R.id.RecyclerView_itemlist);
            LinearLayoutManager itemLinearLayoutManager = new LinearLayoutManager(this);
            RecyclerView_itemlist.setLayoutManager(itemLinearLayoutManager);
        }
    }
////////////////////////////////////////////댓글창
}