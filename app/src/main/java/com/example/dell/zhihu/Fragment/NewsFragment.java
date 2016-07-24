package com.example.dell.zhihu.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.zhihu.Activity.MainActivity;
import com.example.dell.zhihu.Activity.NewsContentActivity;
import com.example.dell.zhihu.Adapter.NewsItemAdapter;
import com.example.dell.zhihu.Model.News;
import com.example.dell.zhihu.Model.StoryBean;
import com.example.dell.zhihu.R;
import com.example.dell.zhihu.Util.Constant;
import com.example.dell.zhihu.Util.HttpUtil;
import com.example.dell.zhihu.db.CacheDBHelper;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

/**
 * Created by DELL on 2016/6/28.
 */
public class NewsFragment extends Fragment {
    private ListView mNewsList;
    private ImageView mTitleImageView;
    private TextView mTitleTextView;
    private  String mId;
    private  String mTitle;
    private ImageLoader mImageLoader;
    private News mNews;
    private NewsItemAdapter mAdapter;
    private CacheDBHelper mHelper;

    public static NewsFragment newInstance(String id,String title){
        NewsFragment newsFragment=new NewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("title",title);
        newsFragment.setArguments(bundle);
        return newsFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId=getArguments().getString("id");
        mTitle=getArguments().getString("title");
        mHelper=new CacheDBHelper(getActivity(),2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_layout,container,false);
        mNewsList= (ListView) view.findViewById(R.id.news_ListView);
        View header=LayoutInflater.from(getActivity()).inflate(R.layout.news_header,mNewsList,false);
        mTitleImageView= (ImageView) header.findViewById(R.id.news_header_ImagerView);
        mTitleTextView= (TextView) header.findViewById(R.id.news_header_TextView);
        mNewsList.addHeaderView(header);
        mImageLoader=ImageLoader.getInstance();
        mNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoryBean story=mNews.getStories().get(position-1);
                Intent intent=new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("story",story);
                startActivity(intent);

            }
        });
        mNewsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mNewsList!=null && mNewsList.getChildCount()!=0){
                    boolean enable=firstVisibleItem==0 && view.getChildAt(firstVisibleItem).getTop()==0;
                    MainActivity.setSwipeRefreshEnable(enable);
                }
            }
        });
        initData();
        return view;
    }
    private void initData(){
        if(HttpUtil.isNetworkConnected(getActivity())){
            HttpUtil.get(Constant.THEMENEWS + mId, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    SQLiteDatabase db=mHelper.getWritableDatabase();
                    db.execSQL("replace into cacheList(date,json) values("+mId+",'"+responseString+"')");
                    db.close();
                    parseJson(responseString);
                }
            });
        }else{
            SQLiteDatabase db=mHelper.getReadableDatabase();
            Cursor cursor=db.rawQuery("select * from cacheList where date="+mId,null);
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
        mNews=gson.fromJson(json, News.class);
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        mTitleTextView.setText(mNews.getDescription());
        mImageLoader.displayImage(mNews.getImage(),mTitleImageView,options);
        mAdapter=new NewsItemAdapter(getActivity(),mNews.getStories());
        mNewsList.setAdapter(mAdapter);
    }

    public void updateTheme(){
        mAdapter.updateTheme();
    }
}
