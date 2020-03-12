package com.example.myaccount.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Adapter.CustomAdapter;
import com.example.myaccount.Adapter.PhotoAdapter;
import com.example.myaccount.Contract.BoardComment;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.PhotoContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoardMyCheckActivity extends AppCompatActivity {


    public TextView title_check, ID_Text_check, day_Text_check, content_Text_check, board_mychecknumber;
    public Button Button_reWrite, Button_delete;
    public FloatingActionButton board_commentwritemy_button;
    private CustomAdapter mAdapter;
    ArrayList<String> Photo = new ArrayList<String>();
    private JSONArray mArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardmycheck);
        board_mychecknumber = findViewById(R.id.board_mychecknumber);
        title_check = findViewById(R.id.title_check);
        ID_Text_check = findViewById(R.id.ID_Text_check);
        day_Text_check = findViewById(R.id.day_Text_check);
        content_Text_check = findViewById(R.id.content_Text_check);
        Button_reWrite = findViewById(R.id.Button_reWrite);
        Button_delete = findViewById(R.id.Button_delete);
        board_commentwritemy_button = findViewById(R.id.board_commentwritemy_button);

        Intent intent = getIntent();


        final long mMemoId = intent.getLongExtra("id", -1);
        final String boardnumber = intent.getLongExtra("id", -1) + "";
        final String title = intent.getStringExtra("title");
        final String contents = intent.getStringExtra("contents");
        final String day = intent.getStringExtra("day");
        final String boardid = intent.getStringExtra("boardid");
        final String useuserID = intent.getStringExtra("userID");
        final String photo = intent.getStringExtra("photo");
        /////////////////////////////

        if (photo.equals(null)) {
            Log.d("사진테스트100", photo);
        } else {
            try {
                mArray = new JSONArray(photo);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("사진테스트15", mArray + "");


            //////////////////////////////////////////////////////////////////////

            for (int i = 0; i < mArray.length(); i++) {
                try {
                    JSONObject jObject = mArray.getJSONObject(i);
                    Log.d("사진테스트20", jObject.getString("photo"));
                    String comment = jObject.getString("photo");
                    Photo.add(comment);
                    Log.d("사진테스트18", Photo.get(i) + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        ///////////////////////////////Json으로 저장된 사진값 가져옴 이제 추가시켜야함
        ///////사진창
        RecyclerView PhotoRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardmyphoto);
        LinearLayoutManager PhotoLinearLayoutManager = new LinearLayoutManager(this);
        PhotoRecyclerView.setLayoutManager(PhotoLinearLayoutManager);

        PhotoAdapter photoAdapter = new PhotoAdapter(this, Photo, useuserID);

        PhotoRecyclerView.setAdapter(photoAdapter);

        ///////////////////////////////////////////////////////////////

        board_mychecknumber.setText("게시글 : " + mMemoId + "");
        title_check.setText("제목 " + title);
        ID_Text_check.setText("ID : " + boardid);
        day_Text_check.setText(day);
        content_Text_check.setText("내용 " + contents);

        Button_reWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardMyCheckActivity.this, BoardReWriteActivity.class);//추후수정

                intent.putExtra("id", mMemoId);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);
                intent.putExtra("day", day);
                intent.putExtra("userID", useuserID);
                intent.putExtra("photo", photo);
                //데이터추가해서 넘기기
                startActivity(intent);
                finish();

            }
        });

        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.delete(BoardContract.BoardEntry.TABLE_NAME, BoardContract.BoardEntry._ID + "=" + mMemoId, null);//데이터삭제를 도와주는 db.delete
                setResult(RESULT_OK);//setResult()를 통해 돌려줄 결과를 저장
                Intent intent = new Intent(BoardMyCheckActivity.this, BoardActivity.class);
                intent.putExtra("userID", useuserID);
                startActivity(intent);
                finish();
            }
        });

        /////////////////////////////댓글창/////////////////
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardmycomment);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ////////////////////////////////////////////////////////처음가져온제이슨어레이///////////
        final SharedPreferences prefs = getSharedPreferences("comment", Activity.MODE_PRIVATE);
        final String json = prefs.getString(mMemoId + "", null);
        Log.d("처음가져온json", json + "");
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
//////////////////////////////////////////////////////


        board_commentwritemy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BoardMyCheckActivity.this);
                View view = LayoutInflater.from(BoardMyCheckActivity.this)
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
                        SharedPreferences prefs = getSharedPreferences("comment", Activity.MODE_PRIVATE);


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

                        String json = prefs.getString(mMemoId + "", null);

                        Log.d("제이슨", json);

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
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardmycomment);
            mRecyclerView.setAdapter(mAdapter);
            //여기서 쓰레드 실행시켜야함 데이터전송 쓰레드 +

        }
    }

    ////////////////////////스트링을 비트맵으로 바꿔주는 함수

}
