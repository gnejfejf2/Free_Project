package com.example.myaccount.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myaccount.Adapter.CustomAdapter;
import com.example.myaccount.Adapter.ItemAdapter;
import com.example.myaccount.Adapter.ReWritePhotoAdapter;
import com.example.myaccount.Contract.BoardComment;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.ItemList;
import com.example.myaccount.Contract.Shoppingbasketcontract;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.Helper.ShoppingbasketDBHelper;
import com.example.myaccount.R;
import com.example.myaccount.Request.MoneyCheckRequest;
import com.example.myaccount.Request.MoneyRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanyBoardUserCheckActivity extends AppCompatActivity {


    public TextView title_check;
    public FloatingActionButton board_commentwrite_button;
    private Button map_Button;
    private CustomAdapter mAdapter;
    private ItemAdapter itemAdapter;
    private RecyclerView RecyclerView_itemlist;
    private ArrayList<ItemList> arrayItemList = new ArrayList<ItemList>();
    private JSONArray itemArray;
    private ArrayList<Integer> pricelist, numberlist,removelist;
    private ArrayList<String> titlelist;
    private int mposition;

    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyboardusercheck);

        title_check = findViewById(R.id.title_check);
        board_commentwrite_button = findViewById(R.id.board_commentwrite_button);
        RecyclerView_itemlist = findViewById(R.id.RecyclerView_itemlist);
        map_Button = findViewById(R.id.map_Button);
        Intent intent = getIntent();


        final long mMemoId = intent.getLongExtra("id", -1);


/////////////////////////////////////////////////////////////////////////////////////
        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String sql = "select * from ComponyBoard where _id = "+mMemoId+";";
        Cursor result = db.rawQuery(sql, null);

        result.moveToFirst();

        final String boardnumber = intent.getLongExtra("id", -1) + "";
        final String title = result.getString(1);

        final String day = result.getString(4);
        final String boardid = result.getString(3);
        final String useuserID = intent.getStringExtra("userID");
        final String itemlist = result.getString(6);
        final String latitude=result.getString(7);
        final String longitude=result.getString(8);
        result.close();


        ///////////////////////////////////////////////////////////////
        title_check.setText("회사명 : " + title);


/////////////////////////////데이터에 저장된 json가져와서 array리스트로 변경시키고 리사이클러뷰에 연동////그리고 그 json값을 다시 넘기고 다음화면에서 array리스트로 변경
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



        ////////////////////////////////클릭리스너
        itemAdapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                final int itemposition = position;
                String st[]={"1. 장바구니","2. 즉시구매"};
                AlertDialog.Builder builder=new AlertDialog.Builder(CompanyBoardUserCheckActivity.this);
                builder.setTitle("선택하십시오");
                builder.setItems(st, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Log.d("테스트","테스트");
                            //boardid 주인장아이디

                            SQLiteDatabase shoppingdb= ShoppingbasketDBHelper.getInstance(getApplicationContext()).getWritableDatabase();
                            ContentValues contentValues=new ContentValues();
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_PRICE,arrayItemList.get(itemposition).getPrice());
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_TITLE,arrayItemList.get(itemposition).getTitle());
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYUSERID,useuserID);
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_SELLUSERID,boardid);
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_PHOTOJSON,arrayItemList.get(itemposition).getPhoto());
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_ISSELECTED,false);
                            contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYNUMBER,1);
                            Log.d("테스트5",contentValues+"");
                            shoppingdb.insert(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME,null,contentValues);
                            Toast.makeText(getApplicationContext(),"장바구니에 추가되었습니다.",Toast.LENGTH_SHORT).show();

                            return;
                        }
                        else if(which==1) {


                            Response.Listener<String> responseListner2 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.d("시작했냐", "시작해라");
                                        boolean success = jsonObject.getBoolean("success");
                                        if (success) {//데이터찾기 실패
                                            titlelist = new ArrayList<>();
                                            pricelist = new ArrayList<>();
                                            numberlist=new ArrayList<>();
                                            removelist=new ArrayList<>();
                                            String userMoney = String.valueOf(jsonObject.getInt("userMoney"));
                                            String itemprice = arrayItemList.get(itemposition).getPrice();
                                            String itemtitle = arrayItemList.get(itemposition).getTitle();
                                            Intent buyintent = new Intent(CompanyBoardUserCheckActivity.this, ItemPaybillActivity.class);
                                            buyintent.putExtra("price", itemprice);
                                            buyintent.putExtra("title", itemtitle);
                                            buyintent.putExtra("userMoney", userMoney);
                                            titlelist.add(itemtitle);
                                            pricelist.add(Integer.valueOf(itemprice));
                                            numberlist.add(1);
                                            buyintent.putExtra("userID",useuserID);
                                            removelist.add(10000);
                                            buyintent.putStringArrayListExtra("titlelist", titlelist);
                                            buyintent.putIntegerArrayListExtra("pricelist", pricelist);
                                            buyintent.putIntegerArrayListExtra("removelist", removelist);
                                            buyintent.putIntegerArrayListExtra("numberlist", numberlist);
                                            startActivity(buyintent);

                                        } else {//데이터찾기 성공

                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };

                            MoneyCheckRequest MoneyCheckRequest = new MoneyCheckRequest(useuserID, useuserID, responseListner2);
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(MoneyCheckRequest);
                            Log.d("시작했냐", "시작해라");

                            return;
                        }
                    }
                });
                AlertDialog dialog=builder.create();
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
        Log.d("배열변환", mArrayList + "");
        mAdapter = new CustomAdapter(this, mArrayList, useuserID);

        mRecyclerView.setAdapter(mAdapter);

/////////////////////////////////////구분선주기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
//////////////////////////////////////////////////////댓글창

        map_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude != null && longitude != null) {
                    Intent intent = new Intent(CompanyBoardUserCheckActivity.this, CustomMarkerClusteringActivity.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("title", title);
                    intent.putExtra("boardid", boardid);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"등록된 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        board_commentwrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyBoardUserCheckActivity.this);
                View view = LayoutInflater.from(CompanyBoardUserCheckActivity.this)
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
            RecyclerView_itemlist = (RecyclerView) findViewById(R.id.RecyclerView_itemlist);
            LinearLayoutManager itemLinearLayoutManager = new LinearLayoutManager(this);
            RecyclerView_itemlist.setLayoutManager(itemLinearLayoutManager);


        }
    }
////////////////////////////////////////////댓글창
}