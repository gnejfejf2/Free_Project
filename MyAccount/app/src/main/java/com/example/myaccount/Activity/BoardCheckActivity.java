package com.example.myaccount.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Adapter.CustomAdapter;
import com.example.myaccount.Adapter.PhotoAdapter;
import com.example.myaccount.Contract.BoardComment;
import com.example.myaccount.Contract.UserData;
import com.example.myaccount.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BoardCheckActivity extends AppCompatActivity {

    public TextView title_check, ID_Text_check, day_Text_check, content_Text_check;
    public FloatingActionButton board_commentwrite_button;
    private CustomAdapter mAdapter;
    ArrayList<String> Photo = new ArrayList<String>();
    private JSONArray mArray;
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY="AAAAKsOmrCY:APA91bEeAoB4YBZwDfASbFGgVJV2dTBNVvhgt4wQmTlO6I3g9RMmLzOI9UmMURjwGo3rTUsh4rJ4fr1t_S7MXZB93EWv74mu9BAQd_QlALPRmNLnm3A5jjuxHO6cHqIQ37DEwoJ_PaMv";
    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardcheck);

        title_check = findViewById(R.id.title_check);
        ID_Text_check = findViewById(R.id.ID_Text_check);
        day_Text_check = findViewById(R.id.day_Text_check);
        content_Text_check = findViewById(R.id.content_Text_check);
        board_commentwrite_button = findViewById(R.id.board_commentwrite_button);

        Intent intent = getIntent();


        final long mMemoId = intent.getLongExtra("id", -1);
        final String boardnumber = intent.getLongExtra("id", -1) + "";
        final String title = intent.getStringExtra("title");
        final String contents = intent.getStringExtra("contents");
        final String day = intent.getStringExtra("day");
        final String boardid = intent.getStringExtra("boardid");
        final String useuserID = intent.getStringExtra("userID");
/////////////////////////////////////////////////////////////////////////////////////
        final String photo = intent.getStringExtra("photo");
        /////////////////////////////

        Log.d("사진테스트15", photo + "");

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
        ///////////////////////////////Json으로 저장된 사진값 가져옴 이제 추가시켜야함
        ///////사진창
        RecyclerView PhotoRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardphoto);
        LinearLayoutManager PhotoLinearLayoutManager = new LinearLayoutManager(this);
        PhotoRecyclerView.setLayoutManager(PhotoLinearLayoutManager);

        PhotoAdapter photoAdapter = new PhotoAdapter(this, Photo, useuserID);

        PhotoRecyclerView.setAdapter(photoAdapter);
        ////////////////////////////////////////////////////////////////////////
        title_check.setText(title);
        ID_Text_check.setText(boardid);
        day_Text_check.setText(day);
        content_Text_check.setText(contents);
        Log.d("boardid", boardid);
        Log.d("userID", useuserID);
        /////////////////////////////댓글창/////////////////
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_boardcomment);
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

        mAdapter = new CustomAdapter(this, mArrayList, useuserID);

        mRecyclerView.setAdapter(mAdapter);

/////////////////////////////////////구분선주기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
//////////////////////////////////////////////////////


        board_commentwrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BoardCheckActivity.this);
                View view = LayoutInflater.from(BoardCheckActivity.this)
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
                        sendPostToFCM(boardid,boardnumber);
                        Log.d("실행했어?", "왜실행안해이제");
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

        }
    }
///////////////////////////////쓰레드를이용하여 내가 선택한 공간에 fcm 토큰을 넘김 이제 받아서 처리 하는 과정만 만들어주면됨
    private void sendPostToFCM(String boardid,String boardnum) {//여기에 유저아이디를 넣어서 토큰을 찾아서 넘겨주는 메소드를 만들면 된다. 이거그대로 사용하고
        String userid =boardid;
        final String mboardnum=boardnum;
        Log.d("Boardid",boardid+"");
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("users").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final UserData userData = dataSnapshot.getValue(UserData.class);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // FMC 메시지 생성 start
                            Log.d("유저토큰", userData.fcmToken + "");
                            JSONObject root = new JSONObject();
                            JSONObject data = new JSONObject();

                            data.put("test", mboardnum);//여기에 게시판 고유번호를 넣어서 전달을 한다
                            data.put("body", "test");
                            data.put("title", getString(R.string.app_name));
                            root.put("data", data);

                            root.put("to", userData.fcmToken);//여기에 상대방 토큰을 알아내는 방법을 찾아서 넣어야함~~~
                            Log.d("여기까지",  "여기까지");
                            // FMC 메시지 생성 end

                            URL Url = new URL(FCM_MESSAGE_URL);
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setRequestProperty("Content-type", "application/json");
                            OutputStream os = conn.getOutputStream();
                            os.write(root.toString().getBytes("utf-8"));
                            os.flush();
                            conn.getResponseCode();

                            Log.d("여기까지",  "여기까지");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}