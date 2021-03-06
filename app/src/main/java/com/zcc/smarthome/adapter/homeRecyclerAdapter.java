package com.zcc.smarthome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.zcc.smarthome.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主界面，流布局适配器
 */

public class homeRecyclerAdapter extends RecyclerView.Adapter<homeRecyclerAdapter.MyViewHolder> {
    private JSONArray lists;
    private Context context;
    private List<Integer> heights = new ArrayList<>();
    private OnItemClickListener mListener;
    private ArrayList<String> color = new ArrayList<>(Arrays.asList("#ccFE4365", "#ccFC9D9A", "#ccF8CDAD", "#ccC8C8A9", "#cc83AF9B"));

    public homeRecyclerAdapter(Context context, JSONArray lists) {
        this.context = context;
        this.lists = lists;
        for (int i = 0; i < lists.size(); i++) {
            heights.add((int) (400 + Math.random() * 250));
        }

    }


    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion);

        void ItemLongClickListener(View view, int postion);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_re, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

//        int width = ((Activity) holder.itemView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
//        Log.i("once", "onBindViewHolder: "+width);

        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
        params.height = heights.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);//把params设置给item布局

//        Log.i("once", "onBindViewHolder: " + color.get(position % 4));


//        holder.item_home.setShadowColor(Color.parseColor("#ff0000"));
//        holder.item_home.setElevation(50);
        holder.item_home.setBackgroundColor(Color.parseColor(color.get(position % color.size())));
        holder.item_home.setRadius(30);

        JSONObject jsonObject = lists.getJSONObject(position);
        holder.mTv.setText(jsonObject.getString("Name"));//为控件绑定数据
        holder.item_home_text1.setText(String.format("%s%s", jsonObject.getString("Value"), jsonObject.getString("Unit")));//为控件绑定数据
        if (mListener != null) {//如果设置了监听那么它就不为空，然后回调相应的方法
            holder.itemView.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                mListener.ItemClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
            });
            holder.itemView.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                mListener.ItemLongClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTv;
        TextView item_home_text1;
        QMUILinearLayout item_home;

        MyViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.textView);
            item_home_text1 = itemView.findViewById(R.id.item_home_text1);
            item_home = itemView.findViewById(R.id.item_home);
        }
    }
}
