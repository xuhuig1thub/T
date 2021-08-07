package com.a.frag;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class NewsTitleFragment extends Fragment {

    private static final String TAG = "NewsTitleFragment";

    private boolean isTwoPane;

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.d(TAG, "onInflate() called with: context = [" + context + "], attrs = [" + attrs + "], savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        View view = inflater.inflate(R.layout.news_title_frag, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);


        MRecyclerView mrv = (MRecyclerView) view.findViewById(R.id.news_title_mrv);
        rv.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager mRvlayoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(rv.getContext(), 8);
//        rv.setLayoutManager(gridLayoutManager);
//        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        rv.setLayoutManager(staggeredGridLayoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        rv.setAdapter(adapter);
        mrv.setItemViewCacheSize(3);

        RecyclerView.RecycledViewPool recycledViewPool=new MRecycledViewPool();
        recycledViewPool.setMaxRecycledViews(NewsAdapter.TYPE_COMMON,6);
        mrv.setRecycledViewPool(recycledViewPool);



        //viewType类型为TYPE_SPECIAL时，设置四级缓存池RecyclerPool不存储对应类型的数据 因为需要开发者自行缓存
        recycledViewPool.setMaxRecycledViews(NewsAdapter.TYPE_101, 0);
        //设置ViewCacheExtension缓存
        mrv.setViewCacheExtension(new MyViewCacheExtension());

        mrv.setLayoutManager(mRvlayoutManager);
//        mrv.addItemDecoration(new com.android.setupwizardlib.DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mrv.setAdapter(adapter);



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called with: savedInstanceState = [" + savedInstanceState + "]");
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true; // 可以找到news_content_layout布局时，为双页模式
        } else {
            isTwoPane = false; // 找不到news_content_layout布局时，为单页模式
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i <= 70; i++) {
            News news = new News();
            news.setTitle("This is news title " + i);
            news.setContent(getRandomLengthContent("This is news content " + i + ". "));
            newsList.add(news);
        }
        return newsList;
    }

    private String getRandomLengthContent(String content) {
        Random random = new Random();
        int length = random.nextInt(10) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(content);
        }
        return builder.toString();
    }


    //实现自定义缓存ViewCacheExtension
    class MyViewCacheExtension extends RecyclerView.ViewCacheExtension {
        @Nullable
        @Override
        public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int viewType) {
            //如果viewType为TYPE_SPECIAL,使用自己缓存的View去构建ViewHolder
            // 否则返回null，会使用系统RecyclerPool缓存或者从新通过onCreateViewHolder构建View及ViewHolder
            Log.d(TAG, "getViewForPositionAndType() called with: recycler = [" + recycler + "], position = [" + position + "], viewType = [" + viewType + "]");
            if(NewsAdapter.TYPE_101 ==viewType||NewsAdapter.TYPE_102 ==viewType){
                return caches.get(position);
            }else{
                return null;
            }

//            return viewType == NewsAdapter.TYPE_1 ? caches.get(position) : null;
        }
    }

    public SparseArray<View> caches = new SparseArray<>();//开发者自行维护的缓存

    class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        //viewType类型 TYPE_COMMON代表普通类型 TYPE_SPECIAL代表特殊类型(此处的View和数据一直不变)
        public static final int TYPE_COMMON = 1;
        public static final int TYPE_101 = 101;
        public static final int TYPE_102 = 102;



        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                Log.d(TAG, "ViewHolder(View) : view = [" + view + "]");
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }         }

        class SpecialHolder extends RecyclerView.ViewHolder {
            TextView tv;
            View itemView;

            public SpecialHolder(@NonNull View itemView) {
                super(itemView);
                this.itemView=itemView;
                tv= (TextView) itemView.findViewById(R.id.news_title);
            }
        }

        class CommonHolder extends RecyclerView.ViewHolder {

            TextView tv_textName;

            public CommonHolder(@NonNull View itemView) {
                super(itemView);
                tv_textName = (TextView) itemView.findViewById(R.id.news_title);
            }
        }



        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @Override
        public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder(ViewGroup parent, int viewType) called with: parent = [" + parent + "], viewType = [" + viewType + "]");
//            View view = LayoutInflater.from().inflate(R.layout.news_item, parent, false);
            final RecyclerView.ViewHolder holder;
            View view;
            if (viewType == TYPE_101) {
                view= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item_special, parent, false);
                holder=new SpecialHolder(view);
            } else {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item, parent, false);
                holder= new ViewHolder(view);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
                    }
                }
            });
            Log.d(TAG, "holder: "+holder);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder(ViewHolder holder, int position) called with: holder = [" + holder + "], position = [" + position + "]");

            News news = mNewsList.get(position);
//            holder.newsTitleText.setText(news.getTitle());
//                if(position==16){
//                    holder.itemView.setLayoutParams(
//                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1500,0));
//
//                }


            if (holder instanceof SpecialHolder) {
                SpecialHolder sHolder = (SpecialHolder) holder;
                if (position==0) {
                    sHolder.tv.setText("DialogFragment");
                    sHolder.itemView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(
                                    new Intent(getActivity().getApplicationContext(),com.a.frag.dialog.FriendActivity.class));
                        }
                });}
                if (position==1) {
                    sHolder.tv.setText("Notification");
                    sHolder.itemView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(
                                    new Intent(getActivity().getApplicationContext(),com.a.notification.NotificationTestActivity.class));
                        }
                });}
                if(position==2){
                    sHolder.tv.setText("PerformanceTest");
                    sHolder.itemView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(
                                    new Intent(getActivity().getApplicationContext(),com.a.test.TraceViewActivity.class));
                        }
                    });
                }
                if(position==3){
                    sHolder.tv.setText("Material Design");
                    sHolder.itemView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(
                                    new Intent(getActivity().getApplicationContext(),com.a.md.MdActivity.class));
                        }
                    });
                }if(position==4){
                    sHolder.tv.setText("Advanced RecyclerView");
                    sHolder.itemView.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            PackageManager packageManager = getActivity().getPackageManager();
                           Intent intent = packageManager.getLaunchIntentForPackage("com.h6ah4i.android.example.advrecyclerview");
                            startActivity(intent);
                        }
                    });
                }
                if(position==16){
                    sHolder.tv.setText("View");
                    sHolder.itemView.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(
                                    new Intent(getActivity().getApplicationContext(),com.a.view.ZoomImgActivity.class));
                        }
                    });
                }
                //这里是重点，根据position将View放到自定义缓存中
                caches.put(position, sHolder.itemView);
            } else if(holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.newsTitleText.setText(news.getTitle());
                if(position==5){
                    viewHolder.newsTitleText.setText("JetPack");
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(
                                    new Intent(getActivity().getApplicationContext(),com.a.jetpack.TestActivity.class));
                        }
                    });
                }
            } }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        @Override
        public int getItemViewType(int position) {
            Log.d(TAG, "getItemViewType() called with: position = [" + position + "]");
//            if(position==16)return 34;
//            return 33;
//            if (position==0) {
//                return TYPE_SPECIAL;//第一个位置View和数据固定
//            } else {
//                return TYPE_COMMON;
//            }

            switch(position){
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 16: return TYPE_101;
                default : return TYPE_COMMON;
            }
        }}}
