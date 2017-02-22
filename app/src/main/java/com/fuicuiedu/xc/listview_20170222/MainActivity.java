package com.fuicuiedu.xc.listview_20170222;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private List<String> datas;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = (ListView) findViewById(R.id.listview);

        datas = new ArrayList<>();

        //添加假数据
        for (int i = 0; i < 50; i++) {
            datas.add("第" + i + "条数据");
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);

        mLv.setAdapter(adapter);

        //动态的设置ListView高度
//        setListViewHeight(mLv);
    }

    //动态的设置ListView高度（拿到所有item的高度，设置到listView身上）
    private void setListViewHeight(ListView listview) {
        //获取listView对应的Adapter
        ListAdapter listAdapter = listview.getAdapter();
        if (listAdapter == null){
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i,null,listview);
            //计算item的宽高（调用一个绘制的方法）
            listItem.measure(0,0);
            //统计所有子项的高度
            totalHeight += listItem.getMeasuredHeight();
        }

        //获取分割线的总高度
        int dividerHeight = listview.getDividerHeight() * (listAdapter.getCount() - 1);

        //拿到listView布局参数
        ViewGroup.LayoutParams parms = listview.getLayoutParams();
        parms.height= totalHeight + dividerHeight;
        listview.setLayoutParams(parms);
    }
}
