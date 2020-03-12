package com.example.myaccount.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.myaccount.Contract.BoardComment;
import com.example.myaccount.Contract.Shoppingresultcontract;
import com.example.myaccount.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ShoppingresultAdapter extends RecyclerView.Adapter<ShoppingresultAdapter.CustomViewHolder> {

    private ArrayList<Shoppingresultcontract> mList;
    private Context mContext;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView price;
        protected TextView number;

        ////ContextMenuListener를 사용하기위해
        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textView_title);
            this.price = (TextView) view.findViewById(R.id.textView_price);
            this.number = (TextView) view.findViewById(R.id.textView_itemnumber);


        }
    }

    public ShoppingresultAdapter(Context context, ArrayList<Shoppingresultcontract> list) {
        mList = list;
        mContext = context;

        //리스트와 컨텍스트 이용
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_shoppingresult, viewGroup, false);
        //리사이클려뷰를 표현할 공간
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {


        viewholder.title.setText(mList.get(position).getTitle());
        viewholder.price.setText("총금액 : " + mList.get(position).getTotalprice()+"개별 금액 : "+ mList.get(position).getPrice());
        viewholder.number.setText("갯수 : "+mList.get(position).getNumber());

        //리사이클려뷰를 보여주고 거기에대한 속성값
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
        //리사이클러뷰의 숫자를 리턴
    }


}
