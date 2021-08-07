package com.a.md;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsContentActivity extends AppCompatActivity {

    private static final String TAG = "NewsContentActivity";

    TextView title,content;
    ImageView iv;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setContentView(R.layout.activity_news_content);
        initView();
    }
        private  void initView(){
        iv= (ImageView) findViewById(R.id.iv_nc);
        content=(TextView)findViewById(R.id.tv_content_nc);
        title=(TextView)findViewById(R.id.tv_title_nc);
        toolbar= (Toolbar) findViewById(R.id.toolbar_news_content);
            setSupportActionBar(toolbar);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBarLayout_nc);
            actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            //让Toolbar的标题固定在上面
            collapsingToolbarLayout.setTitleEnabled(false);

            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            String url = "http://news-at.zhihu.com/api/4/news/"+id;
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String string = response.body().string();
                    Log.e(TAG,string);
                    Gson gson = new Gson();
                    final ContentBean bean = gson.fromJson(string, ContentBean.class);

                    Log.i("felix", "当前线程ID："+Thread.currentThread().getId());

                    final Spanned spanned = Html.fromHtml(bean.getBody(), Html.FROM_HTML_MODE_COMPACT,new UrlImageGetter(
                            NewsContentActivity.this), null);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            content.setText(spanned);
                            Log.e(TAG,"bean==null:"+bean.getImage());
                            Glide.with(NewsContentActivity.this).load(bean.getImage()).into(iv);

                            title.setText(bean.getTitle());
                        }
                    });

                }
            });

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("felix","1111");
                }
            });
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
