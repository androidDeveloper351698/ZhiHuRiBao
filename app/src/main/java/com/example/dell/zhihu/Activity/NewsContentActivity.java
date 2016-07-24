package com.example.dell.zhihu.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.dell.zhihu.Model.Content;
import com.example.dell.zhihu.Model.StoryBean;
import com.example.dell.zhihu.R;
import com.example.dell.zhihu.Util.Constant;
import com.example.dell.zhihu.Util.HttpUtil;
import com.example.dell.zhihu.Util.SPUtil;
import com.example.dell.zhihu.db.CacheDBHelper;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by DELL on 2016/6/30.
 */
public class NewsContentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CoordinatorLayout mCoordinator;
    private WebView mWebView;
    private StoryBean mStory;
    private Content mContent;
    private CacheDBHelper mHelper;
    private FloatingActionButton mFloatBtn;
    private Boolean mIsStar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_layout);
        mHelper=new CacheDBHelper(this,2);
        mCoordinator= (CoordinatorLayout) findViewById(R.id.news_content_coordinator);
        mToolbar= (Toolbar) findViewById(R.id.news_content_toolBar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.light_toolbar));
        mToolbar.setTitle("发现更大的世界");
        mStory= (StoryBean) getIntent().getSerializableExtra("story");
        mFloatBtn= (FloatingActionButton) findViewById(R.id.news_content_floatBtn);
        mIsStar=SPUtil.newInstance(NewsContentActivity.this).get(mStory.getId()+"");
        //如果被收藏
        if(!mIsStar){
            mFloatBtn.setVisibility(View.INVISIBLE);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mWebView= (WebView) findViewById(R.id.news_content_webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.newInstance(NewsContentActivity.this).saveBoolean(mStory.getId()+"",false);
                Snackbar.make(mWebView,"收藏成功",Snackbar.LENGTH_SHORT).show();
                mFloatBtn.setVisibility(View.INVISIBLE);
                SQLiteDatabase db=mHelper.getWritableDatabase();
                db.execSQL("insert into starList (newsType,newsId,newsTitle,newsImage) values ("+mStory.getType()+","+mStory.getId()+",'"+mStory.getTitle()+"','"+mStory.getImages().get(0)+"')");
            }
        });

        if(HttpUtil.isNetworkConnected(NewsContentActivity.this)){
            HttpUtil.get(Constant.CONTENT + mStory.getId(), new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    SQLiteDatabase db=mHelper.getWritableDatabase();
                    responseString = responseString.replaceAll("'", "''");
                    db.execSQL("replace into contentCache (newsId,json) values("+mStory.getId()+",'"+responseString+"')");
                    db.close();
                    parseJson(responseString);
                }
            });
        }else {
            SQLiteDatabase db=mHelper.getReadableDatabase();
            Cursor cursor= db.rawQuery("select * from contentCache where newsId="+mStory.getId(),null);
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
