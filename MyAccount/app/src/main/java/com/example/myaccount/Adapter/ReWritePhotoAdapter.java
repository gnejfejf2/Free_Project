package com.example.myaccount.Adapter;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myaccount.Activity.BoardReWriteActivity;
import com.example.myaccount.R;

import java.util.ArrayList;


public class ReWritePhotoAdapter extends RecyclerView.Adapter<ReWritePhotoAdapter.CustomViewHolder> {

    private ArrayList<String> mList;
    private Context mContext;
    private Long mMemoId;
    private OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView photo;


        ////ContextMenuListener를 사용하기위해
        private CustomViewHolder(View view) {
            super(view);
            this.photo = (ImageView) view.findViewById(R.id.photo_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {

                            mListener.onItemClick(v, position);


                        }
                        return;
                    }
                    return;
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mLongListener != null) {
                            mLongListener.onItemLongClick(v, position);
                            return true;

                        }

                        return true;
                    }
                    return true;
                }
            });


        }

    }

    public ReWritePhotoAdapter(Context context, ArrayList<String> list, Long MemoId) {
        mList = list;
        mContext = context;
        mMemoId = MemoId;
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

        Log.d("사진테스트28", uri + "");


        //리사이클려뷰를 보여주고 거기에대한 속성값
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
        //리사이클러뷰의 숫자를 리턴
    }


    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mLongListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}