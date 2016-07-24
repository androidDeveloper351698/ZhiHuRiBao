package com.example.dell.zhihu.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.zhihu.Adapter.NewsItemAdapter;
import com.example.dell.zhihu.Model.StoryBean;
import com.example.dell.zhihu.R;
import com.example.dell.zhihu.db.CacheDBHelper;

import java.util.ArrayList;

public class StarActivity extends AppCompatActivity {
    private ListView mStarListView;
    private CacheDBHelper mHelper;
    private ArrayList<StoryBean> mStars;
    private NewsItemAdapter mAdapter;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_star);
        mToolBar= (Toolbar) findViewById(R.id.star_toolBar);
        mToolBar.setTitle("我的收藏");
        mToolBar.setBackgroundColor(getResources().getColor(R.color.light_toolbar));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mStarListView= (ListView) findViewById(R.id.star_listView);
        mHelper=new CacheDBHelper(StarActivity.this,2);
        SQLiteDatabase db=mHelper.getReadableDatabase();
        mStars=new ArrayList<>();
       Cursor cursor= db.rawQuery("select newsType,newsId,newsTitle,newsImage from starList",null);
        while (cursor.moveToNext()){
            int type=cursor.getInt(cursor.getColumnIndex("newsType"));
            int id=cursor.getInt(cursor.getColumnIndex("newsId"));
            String title=cursor.getString(cursor.getColumnIndex("newsTitle"));
            String image=cursor.getString(cursor.getColumnIndex("newsImage"));
            StoryBean story=new StoryBean();
            story.setType(type);
            story.setId(id);
            story.setTitle(title);
            ArrayList<String> images=new ArrayList<>();
            images.add(image);
            story.setImages(images);
            mStars.add(story);
        }
       mAdapter=new NewsItemAdapter(StarActivity.this,mStars);
        mStarListView.setAdapter(mAdapter);

        mStarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoryBean mStory=mStars.get(position);
                Intent intent=new Intent(StarActivity.this,NewsContentActivity.class);
                intent.putExtra("story",mStory);
                startActivity(intent);
            }
        });
    }
}
