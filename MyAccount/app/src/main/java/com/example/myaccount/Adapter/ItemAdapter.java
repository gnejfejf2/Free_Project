package com.example.myaccount.Adapter;


import android.content.Context;

import android.net.Uri;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.myaccount.Contract.ItemList;
import com.example.myaccount.R;


import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CustomViewHolder> {

    private ArrayList<ItemList> mList;
    private Context mContext;
    private String mNowuserID;
    private ItemAdapter.OnItemClickListener mListener = null;
    private ItemAdapter.OnItemLongClickListener mLongListener = null;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView price;
        protected ImageView titlephoto;


        private CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.itemboard_name);
            this.price = (TextView) view.findViewById(R.id.itemboard_price);
            this.titlephoto = (ImageView) view.findViewById(R.id.itemtitle_imageView);


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

    public ItemAdapter(Context context, ArrayList<ItemList> list) {
        mList = list;
        mContext = context;

        //리스트와 컨텍스트 이용
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_itemboard, viewGroup, false);
        //리사이클려뷰를 표현할 공간
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {


        viewholder.name.setText(mList.get(position).getTitle());
        viewholder.price.setText(mList.get(position).getPrice() + "원");
        Uri uri = Uri.parse(mList.get(position).getPhoto());


        Glide.with(mContext).load(uri).into(viewholder.titlephoto);

        //리사이클려뷰를 보여주고 거기에대한 속성값
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
        //리사이클러뷰의 숫자를 리턴
    }

    public void setOnItemClickListener(ItemAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(ItemAdapter.OnItemLongClickListener listener) {
        this.mLongListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}