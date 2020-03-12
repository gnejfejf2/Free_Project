package com.example.myaccount.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myaccount.R;
import java.util.ArrayList;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.CustomViewHolder> {

    private ArrayList<String> mList;
    private Context mContext;
    private String mNowuserID;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView photo;


        ////ContextMenuListener를 사용하기위해
        public CustomViewHolder(View view) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.photo_photo);



        }


    }

    public PhotoAdapter(Context context, ArrayList<String> list, String nowuserID) {
        mList = list;
        mContext = context;
        mNowuserID = nowuserID;
        //리스트와 컨텍스트 이용
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_photo, viewGroup, false);
        //리사이클려뷰를 표현할 공간
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder viewholder, int position) {




        Uri uri = Uri.parse(mList.get(position));


        Glide.with(mContext).load(uri).into(viewholder.photo);

        Log.d("사진테스트28",uri+"");


        //리사이클려뷰를 보여주고 거기에대한 속성값
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
        //리사이클러뷰의 숫자를 리턴
    }


}