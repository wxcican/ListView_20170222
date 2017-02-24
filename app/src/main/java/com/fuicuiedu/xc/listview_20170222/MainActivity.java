package com.fuicuiedu.xc.listview_20170222;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private List<String> datas;
    private ArrayAdapter<String> adapter;
    private Handler handler;
    private int visblelastindex;// 可见的最后一条数据下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        mLv = (ListView) findViewById(R.id.listview);

        datas = new ArrayList<>();

        //添加假数据
        for (int i = 0; i < 50; i++) {
            datas.add("第" + i + "条数据");
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);

        mLv.setAdapter(adapter);

        //给listview加一个底部布局，点击即加载数据
//        addMoreView(mLv);

        //动态的设置ListView高度
//        setListViewHeight(mLv);

        mLv.setOnScrollListener(listener);
    }


    //ListView三种滑动状态
    //手指按下移动的状态
    //SCROLL_STATE_TOUCH_SCROLL //触摸滑动
    //惯性滚动状态
    //SCROLL_STATE_FLING //滑翔
    //静止状态
    //SCROLL_STATE_IDLE //静止


    //目的：当用户滑动到listView底部时，自动加载数据
    //实现：判断是否是静止状态，判断是否滑动到了底部（最后一条数据）
    private AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当滚动状态改变时触发的方法
        //最后一条数据的下标
        int lastIndex = adapter.getCount() - 1;
        //判断是否是静止状态，判断是否滑动到了底部（最后一条数据）
        if (scrollState == SCROLL_STATE_IDLE && visblelastindex == lastIndex){
            //加载数据
            loadMoreData();
            adapter.notifyDataSetChanged();
        }
    }

        //参数说明
        //firstVisibleItem//第一个可见的item
        //visibleItemCount//可见的item数量
        //totalItemCount//所有item数量
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当listview滑动时触发的方法

        visblelastindex = firstVisibleItem + visibleItemCount - 1;

        Log.e("======================","=======================");
        Log.e("firstVisibleItem = ",firstVisibleItem + "");
        Log.e("visibleItemCount = ",visibleItemCount + "");
        Log.e("totalItemCount = ",totalItemCount + "");
        Log.e("======================","=======================");
    }
};






































    //给listview加一个底部布局，点击即加载数据
    private void addMoreView(ListView listView){
        //拿到底部布局（加载更多的View）
        View view = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.view_more,null);
        //给listview加一个底部布局
        listView.addFooterView(view);

        final Button moreBtn = (Button) view.findViewById(R.id.view_more_btn);
        final ProgressBar morePrb = (ProgressBar) view.findViewById(R.id.view_more_prb);

//        点击即加载数据
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //视图操作，更新UI
                moreBtn.setVisibility(View.INVISIBLE);
                morePrb.setVisibility(View.VISIBLE);
                //模拟请求网络数据，用handler发送一个延迟任务
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载更多
                        loadMoreData();
                        //视图操作，更新UI
                        moreBtn.setVisibility(View.VISIBLE);
                        morePrb.setVisibility(View.INVISIBLE);
                        //刷新数据
                        adapter.notifyDataSetChanged();
                    }
                },2000);
            }
        });
    }

    //加载更多
    private void loadMoreData() {
        for (int i = 0; i < 5; i++) {
            datas.add("第" + i + "条数据（新）");
        }
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
