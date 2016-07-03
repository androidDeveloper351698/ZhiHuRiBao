package com.example.dell.zhihu.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.zhihu.Activity.MainActivity;
import com.example.dell.zhihu.Model.NewsListItems;
import com.example.dell.zhihu.R;
import com.example.dell.zhihu.Util.Constant;
import com.example.dell.zhihu.Util.HttpUtil;
import com.example.dell.zhihu.Util.SPUtil;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by DELL on 2016/6/23.
 */
public class MenuFragment extends Fragment {

    private TextView mStarTextView;
    private TextView mLoginTextView;
    private TextView mMainTextView;
    private TextView mDownloadTextView;
    private ListView mItemListView;
    private LinearLayout mMenuLinear;
    private List<NewsListItems> mItems;
    private Handler mHandler = new Handler();
    private static final String TAG="MenuFragment";
    private NewsItemsAdapter mAdapter;
    private boolean isLight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"-----------");
        isLight=SPUtil.newInstance(getActivity()).get("isLight");
        View view = inflater.inflate(R.layout.menu, container, false);
        mStarTextView = (TextView) view.findViewById(R.id.menu_StarTextView);
        mDownloadTextView = (TextView) view.findViewById(R.id.menu_DownloadTextView);
        mLoginTextView = (TextView) view.findViewById(R.id.menu_loginTextView);
        mMainTextView = (TextView) view.findViewById(R.id.menu_main_TextView);
        mItemListView = (ListView) view.findViewById(R.id.menu_listView);
        mMenuLinear = (LinearLayout) view.findViewById(R.id.menu_ll);
        getItems();
        mMenuLinear.setBackgroundColor(getResources().getColor(isLight ? R.color.light_menu_header : R.color.dark_menu_header));
        mLoginTextView.setTextColor(getResources().getColor(isLight ? R.color.light_menu_header_tv : R.color.dark_menu_header_tv));
        mStarTextView.setTextColor(getResources().getColor(isLight ? R.color.light_menu_header_tv : R.color.dark_menu_header_tv));
        mDownloadTextView.setTextColor(getResources().getColor(isLight ? R.color.light_menu_header_tv : R.color.dark_menu_header_tv));
        mMainTextView.setBackgroundColor(getResources().getColor(isLight ? R.color.light_menu_index_background : R.color.dark_menu_index_background));
        mItemListView.setBackgroundColor(getResources().getColor(isLight ? R.color.light_menu_listview_background : R.color.dark_menu_listview_background));
        mMainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_content,new MainFragment(),"latest").commit();
                MainActivity.setCurFragment();
                MainActivity.closeMenu();
            }
        });
        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Log.i(TAG,mItems.get(position).getId()+"-----------"+mItems.get(position).getTitle());
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_content,NewsFragment.newInstance(mItems.get(position).getId(),mItems.get(position).getTitle()),"news").commit();
                MainActivity.closeMenu();
            }
        });
        return view;
    }

    private void getItems() {
        mItems = new ArrayList<>();
        HttpUtil.get(Constant.THEMES, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray itemsArray = response.getJSONArray("others");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        NewsListItems item = new NewsListItems();
                        JSONObject itemObject = itemsArray.getJSONObject(i);
                        item.setTitle(itemObject.getString("name"));
                        item.setId(itemObject.getString("id"));
                        mItems.add(item);
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter=new NewsItemsAdapter();
                            mItemListView.setAdapter(new NewsItemsAdapter());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

class NewsItemsAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(getActivity()).inflate(R.layout.menu_item,parent,false);
        }
        TextView mItemTextView= (TextView) convertView.findViewById(R.id.menu_item_TextView);
        mItemTextView.setText(mItems.get(position).getTitle());
        return convertView;
    }



}
    public void updateTheme(){
        isLight= SPUtil.newInstance(getActivity()).get("isLight");
        mMenuLinear.setBackgroundColor(getResources().getColor(isLight ? R.color.light_menu_header : R.color.dark_menu_header));
        mLoginTextView.setTextColor(getResources().getColor(isLight ? R.color.light_menu_header_tv : R.color.dark_menu_header_tv));
        mStarTextView.setTextColor(getResources().getColor(isLight ? R.color.light_menu_header_tv : R.color.dark_menu_header_tv));
        mDownloadTextView.setTextColor(getResources().getColor(isLight ? R.color.light_menu_header_tv : R.color.dark_menu_header_tv));
        mMainTextView.setBackgroundColor(getResources().getColor(isLight ? R.color.light_menu_index_background : R.color.dark_menu_index_background));
        mItemListView.setBackgroundColor(getResources().getColor(isLight ? R.color.light_menu_listview_background : R.color.dark_menu_listview_background));
        mAdapter.notifyDataSetChanged();
    }


}
