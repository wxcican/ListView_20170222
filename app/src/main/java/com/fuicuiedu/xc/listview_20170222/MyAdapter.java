package com.fuicuiedu.xc.listview_20170222;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class MyAdapter extends BaseAdapter{

    private List<String> mDatas;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(List<String> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();

        holder.tv.setText(mDatas.get(5));

        return convertView;
    }


    private class ViewHolder{
        TextView tv;
        Button btn;

        public ViewHolder(View view){
            tv = (TextView) view.findViewById(R.id.item_tv);
            btn = (Button) view.findViewById(R.id.item_btn);
        }
    }
}
