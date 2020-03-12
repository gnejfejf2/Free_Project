package com.example.myaccount.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Activity.BoardMyCheckActivity;
import com.example.myaccount.Activity.StartActivity;
import com.example.myaccount.Contract.BoardComment;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<BoardComment> mList;
    private Context mContext;
    private String mNowuserID;

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView comment;
        protected TextView id;
        protected TextView day;

////ContextMenuListener를 사용하기위해
        public CustomViewHolder(View view) {
            super(view);
            this.comment = (TextView) view.findViewById(R.id.boardcomment_content);
            this.id = (TextView) view.findViewById(R.id.boardcomment_userID);
            this.day = (TextView) view.findViewById(R.id.boardcomment_day);

            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
            //컨텍스트 메뉴 만들고
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //리스너만들고 각각편집과 삭제에대한 값입력
                final String _id = mList.get(getAdapterPosition()).getNumber();
                Log.d("동등한값 비교",mList.get(getAdapterPosition()).getId()+"");
                if (mNowuserID.equals(mList.get(getAdapterPosition()).getId())) {
                switch (item.getItemId()) {
                    case 1001:

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext)
                                .inflate(R.layout.edit_box, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final EditText editTextcommonet = (EditText) view.findViewById(R.id.edittext_dialog_content);
                        final TextView editTextid = (TextView) view.findViewById(R.id.edittext_dialog_id);
                        final TextView editTextday = (TextView) view.findViewById(R.id.edittext_dialog_day);

                        editTextcommonet.setText(mList.get(getAdapterPosition()).getComment());
                        editTextid.setText(mList.get(getAdapterPosition()).getId());
                        editTextday.setText(mList.get(getAdapterPosition()).getDay());
                        //입력되있는값 가져오기
                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String strcomment = editTextcommonet.getText().toString();
                                String strid = editTextid.getText().toString();
                                String strday = editTextday.getText().toString();

                                BoardComment BoardComment = new BoardComment(strcomment, strid, strday, _id);

                                mList.set(getAdapterPosition(), BoardComment);
                                notifyItemChanged(getAdapterPosition());


                                SharedPreferences prefs = mContext.getSharedPreferences("comment", Activity.MODE_PRIVATE);

                                SharedPreferences.Editor editor = prefs.edit();
                                JSONArray jArray = new JSONArray();//배열이 필요할때
                                try {

                                    for (int i = 0; i < mList.size(); i++) {
                                        JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                        sObject.put("comment", mList.get(i).getComment());
                                        sObject.put("id", mList.get(i).getId());
                                        sObject.put("day", mList.get(i).getDay());
                                        sObject.put("number", mList.get(i).getNumber());
                                        jArray.put(sObject);
                                    }

                                    Log.d("JSON Test", jArray.toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                editor.putString("_id", jArray.toString());
                                editor.commit();


                                dialog.dismiss();
                                //데이터 편집
                            }
                        });

                        dialog.show();

                        break;

                    case 1002:

                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mList.size());
                        SharedPreferences prefs = mContext.getSharedPreferences("comment", Activity.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefs.edit();
                        JSONArray jArray = new JSONArray();//배열이 필요할때
                        try {

                            for (int i = 0; i < mList.size(); i++) {
                                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                sObject.put("comment", mList.get(i).getComment());
                                sObject.put("id", mList.get(i).getId());
                                sObject.put("day", mList.get(i).getDay());
                                sObject.put("number", mList.get(i).getNumber());
                                jArray.put(sObject);
                            }

                            Log.d("JSON Test", jArray.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editor.putString(_id, jArray.toString());
                        editor.commit();
                        break;
                    //그위치에 있는 값을 제거
                }
            }
                else{
                    Log.d("어뎁터userID",mNowuserID+"");
                    Toast.makeText(mContext, "권한이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                    return true;
                }

        };
    }

    public CustomAdapter(Context context, ArrayList<BoardComment> list,String nowuserID) {
        mList = list;
        mContext = context;
        mNowuserID=nowuserID;
        //리스트와 컨텍스트 이용
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_boardcomment, viewGroup, false);
        //리사이클려뷰를 표현할 공간
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {



        viewholder.comment.setText(mList.get(position).getComment());
        viewholder.id.setText("작성자 : "+mList.get(position).getId());
        viewholder.day.setText(mList.get(position).getDay());

        //리사이클려뷰를 보여주고 거기에대한 속성값
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
        //리사이클러뷰의 숫자를 리턴
    }


}