package com.example.dell.zhihu.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.dell.zhihu.Model.Content;
import com.example.dell.zhihu.Model.StoryBean;
import com.example.dell.zhihu.R;
import com.example.dell.zhihu.Util.Constant;
import com.example.dell.zhihu.Util.HttpUtil;
import com.example.dell.zhihu.Util.SPUtil;
import com.example.dell.zhihu.db.CacheDBHelper;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

/**
 * Created by DELL on 2016/6/30.
 */
public class LatestContentActivity extends AppCompatActivity {

    private StoryBean story;

    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBar;
    private Toolbar mToolBar;
    private ImageView mImageView;
    private WebView mWebView;
    private CollapsingToolbarLayout mCollapsing;
    private Content mContent;
    private boolean isLight;
    private CacheDBHelper mHelper;
    private FloatingActionButton mFloatBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latest_content_layout);
        mHelper=new CacheDBHelper(this,1);
        isLight= SPUtil.newInstance(LatestContentActivity.this).get("isLight");
        story= (StoryBean) getIntent().getSerializableExtra("story");
        mAppBar= (AppBarLayout) findViewById(R.id.latest_appBar);
        //mAppBar.setVisibility(View.INVISIBLE);
        mToolBar= (Toolbar) findViewById(R.id.latest_ToolBar);
        setSupportActionBar(mToolBar);
        //给ToolBar设置一个向左的小箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCollapsing= (CollapsingToolbarLayout) findViewById(R.id.latest_collapsing);
        mCollapsing.setTitle(story.getTitle());
        mCollapsing.setContentScrimColor(getResources().getColor(isLight?R.color.light_toolbar: R.color.dark_toolbar));
        mCollapsing.setStatusBarScrimColor(getResources().getColor(isLight?R.color.light_toolbar:R.color.dark_toolbar));


        mImageView= (ImageView) findViewById(R.id.latest_ImageView);
        mWebView= (WebView) findViewById(R.id.latest_webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(isLight?R.color.light_news_item:R.color.light_news_topic));

        mFloatBtn= (FloatingActionButton) findViewById(R.id.latest_content_floatBtn);
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCollapsing,"收藏成功",Snackbar.LENGTH_SHORT).show();
                mFloatBtn.setVisibility(View.INVISIBLE);
            }
        });
        if(HttpUtil.isNetworkConnected(this)){
            HttpUtil.get(Constant.CONTENT + story.getId(), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    SQLiteDatabase db=mHelper.getWritableDatabase();
                    responseString = responseString.replaceAll("'", "''");
                    db.execSQL("replace into contentCache (newsId,json) values("+story.getId()+",'"+responseString+"')");
                    db.close();
                    parseJson(responseString);
                }
            });
        }
        else {
                SQLiteDatabase db=mHelper.getReadableDatabase();
               Cursor cursor= db.rawQuery("select * from contentCache where newsId="+story.getId(),null);
                if(cursor.moveToFirst()){
                    String json=cursor.getString(cursor.getColumnIndex("json"));
                    parseJson(json);
                }
            cursor.close();
            db.close();
        }
    }
    private void parseJson(String json){
        Gson gson=new Gson();
        mContent=gson.fromJson(json,Content.class);
        ImageLoader mImageLoader=ImageLoader.getInstance();
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        mImageLoader.displayImage(mContent.getImage(),mImageView,options);
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + mContent.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,R.anim.slide_out_to_left_from_right);
    }
}
