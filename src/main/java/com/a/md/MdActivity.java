package com.a.md;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MdActivity extends AppCompatActivity {

    private static final String TAG = "MdActivity";

    private boolean uploading=false;

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionBtn;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);
        //获取ActionBar对象
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Toast.makeText(this, "actionBar!=null", Toast.LENGTH_SHORT).show();
            //设置按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            //更换按钮图标（默认是返回的箭头）
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_moreoverflow);
        }
        initView();
        initToolBar();
        initAdapter();
        refreshData();
    }

    private void initToolBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_md);
        getMenuInflater().inflate(R.menu.toolbar,toolbar.getMenu());
      setSupportActionBar(toolbar);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_moreoverflow);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationView_md:  //弹出DrawerLayout菜单，参数为弹出的方式
                        drawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case R.id.backup_md:
                        if(animationDrawable==null)
                            animationDrawable= (AnimationDrawable)item.getIcon();
                        if(false==uploading){
                            animationDrawable.start();
                            Toast.makeText(MdActivity.this, "backup is going on", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            animationDrawable.stop();
                            Toast.makeText(MdActivity.this, "backup is stopped", Toast.LENGTH_SHORT).show();
                        }
                        uploading=!uploading;
                        break;
                    case R.id.settings_md:
                        Toast.makeText(MdActivity.this, "settings ic clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_md);
        mNavigationView= (NavigationView) findViewById(R.id.nav_activity_md);
        mFloatingActionBtn=(FloatingActionButton) findViewById(R.id.fab_md);
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_view_md);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_activity_main);

        mNavigationView.setCheckedItem(R.id.mail_md);
                 //给菜单设置监听
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                //关闭弹出菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //调用initData方法获取数据
                refreshData();
            }
        });


    }

    List<ResultBean.StoriesBean> lists;
    OkHttpClient okHttpClient = new OkHttpClient();
   private void refreshData(){
        new Thread() {
            @Override
            public void run() {
                super.run();

                //新闻url
                String url = "http://news-at.zhihu.com/api/4/news/latest";
                Request request = new Request.Builder().url(url).build();
                Call call = okHttpClient.newCall(request);
                //异步获取数据
                call.enqueue(new Callback() {
                    @Override
                    //获取失败的回调
                    public void onFailure(Call call, IOException e) {
                        //关闭刷新提示
                        swipeRefreshLayout.setRefreshing(false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MdActivity.this, "无网络", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            Log.i("felix", string);
                            //Gson解析获取的数据
                            Gson gson = new Gson();
                            ResultBean resultBean = gson.fromJson(string, ResultBean.class);
                            Log.e(TAG,resultBean.toString());
                            //重置之前的List引用
                            lists = null;
                            //获取Gson解析后的数据
                            lists = resultBean.getStories();
                            adapter.updateLists(lists);
                            Log.i("felix", lists.size()+"");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //修改List引用对象

                                    //通知刷新
                                    adapter.notifyDataSetChanged();
                                    //关闭刷新提示
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(MdActivity.this, "无网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }.start();
   }



    public void fabOnClick(View view){
            //setAction来指定操作  第一个参数是操作名称  第二个参数是点击事件的处理逻辑
            Snackbar sb=Snackbar.make(view, "删除数据", Snackbar.LENGTH_SHORT);
            sb.getView().setAlpha(0.5f);
            sb.setAction("确定",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MdActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    }).setAction("I'm not sure",new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(MdActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();
                }
            });
            sb.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_SHORT).show();
       getMenuInflater().inflate(R.menu.toolbar,menu);
       return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Toast.makeText(this, "onMenuOpened(int featureId, Menu menu)", Toast.LENGTH_SHORT).show();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Toast.makeText(this, "onOptionsMenuClosed(Menu menu)", Toast.LENGTH_SHORT).show();

        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        Toast.makeText(this, "onContextMenuClosed(Menu menu) ", Toast.LENGTH_SHORT).show();
        super.onContextMenuClosed(menu);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
        animationDrawable= (AnimationDrawable) toolbar.getMenu().getItem(1).getIcon();
        animationDrawable.stop();
        Toast.makeText(this, "animationDrawable.isRunning():" + animationDrawable.isRunning(), Toast.LENGTH_SHORT).show();
        animationDrawable.selectDrawable(0);
        animationDrawable.invalidateSelf();
        if(uploading&&hasFocus) {
            Toast.makeText(this, "onWindowFocusChanged(boolean hasFocus) ----->hasFocus:" + hasFocus+"-----uploading:"+uploading, Toast.LENGTH_SHORT).show();
             animationDrawable.start();
         }
////             mHandler.post(new Runnable() {
////                 @Override
////                 public void run() {
//////                     animationDrawable.stop();
//////                     animationDrawable.selectDrawable(0);
////
////                 }
////             });
//
//        }
    }

    Handler mHandler;
    AnimationDrawable  animationDrawable;
    MyAdapter adapter;

    //重写该方法，箭头菜单按钮的点击事件
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            //该按钮的Id已经在android的R文件中定义，为 ：android.R.id.home
            switch (item.getItemId()){
                case android.R.id.home:  //弹出DrawerLayout菜单，参数为弹出的方式
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
                case R.id.backup_md:
                    if(animationDrawable==null)
                    animationDrawable= (AnimationDrawable)item.getIcon();
                    if(false==uploading){
                        animationDrawable.start();
                        Toast.makeText(this, "backup is going on", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        animationDrawable.stop();
                        Toast.makeText(this, "backup is stopped", Toast.LENGTH_SHORT).show();
                    }
                    uploading=!uploading;
                    break;
                case R.id.settings_md:
                    Toast.makeText(this, "settings ic clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

       private void  initAdapter(){
            List<String> titleList = new ArrayList<>();
            List<String> imgUrlList = new ArrayList<>();
            for(int i = 0; i < 40; i++){
                titleList.add("DATA"+i);
                imgUrlList.add(" ");
            }
            //创建适配器  并传入模拟的数据
            adapter= new MyAdapter(titleList,imgUrlList);
            //设置显示格式 2列
            StaggeredGridLayoutManager layoutManager =
                    new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            //将显示格式传给mRecyclerView
            mRecyclerView.setLayoutManager(layoutManager);
            //设置适配器
            mRecyclerView.setAdapter(adapter);

        }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<String> tittleList;
        private List<String> urlList;
        private List<String> idList;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_md,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,final int position) {
            holder.tv.setText(tittleList.get(position));
            Glide.with(MdActivity.this).load(urlList.get(position))
                    .placeholder(R.mipmap.ic_menu_report_image).into(holder.iv);
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MdActivity.this,
                            com.a.md.NewsContentActivity.class).putExtra("id",idList.get(position)));
                }
            });
        }

        @Override
        public int getItemCount() {
            return tittleList.size();
        }

        public void updateLists(List<ResultBean.StoriesBean> lists) {
            tittleList.clear();
            urlList.clear();
            for(ResultBean.StoriesBean sb:lists){
                tittleList.add(sb.getTitle());
                urlList.add(sb.getImages().get(0));
                idList.add(sb.getId());
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tv;
            AppCompatImageView iv;
            public ViewHolder(View itemView) {
                super(itemView);
                tv= (TextView)itemView. findViewById(R.id.tv_rv_item_md);
                iv= (AppCompatImageView) itemView.findViewById(R.id.iv_rv_item_md);
            }
        }

        public MyAdapter(List<String> lists,List<String> imgUrlList){
            tittleList = lists;
            urlList=imgUrlList;
            idList=new ArrayList<String>();

        }

    }


}
