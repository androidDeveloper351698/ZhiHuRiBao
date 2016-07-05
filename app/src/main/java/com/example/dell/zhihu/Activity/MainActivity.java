package com.example.dell.zhihu.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.dell.zhihu.Fragment.MainFragment;
import com.example.dell.zhihu.Fragment.MenuFragment;
import com.example.dell.zhihu.Fragment.NewsFragment;
import com.example.dell.zhihu.R;
import com.example.dell.zhihu.Util.SPUtil;
import com.example.dell.zhihu.db.CacheDBHelper;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity{

    private Toolbar mToolBar;
    private static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private  static SwipeRefreshLayout mSwipe;
    private long mFirstTime;
    private FrameLayout mContent;
    private boolean mIsLight;
    //当前的Fragment
    private static  String curFragment;
    private CacheDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Logger.init("MainActivity");
        Log.i("MainActivity","Hello World!");
        Log.d("MainActivity","===============");
        Logger.i("Hello world");
        Logger.d("Hello-----------------");
        mDbHelper=new CacheDBHelper(this,1);
        mIsLight=SPUtil.newInstance(MainActivity.this).get("isLight");
        mContent= (FrameLayout) findViewById(R.id.main_content);
       /* MenuFragment fragment=new MenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_menuFragment,fragment).commit();*/
        mToolBar= (Toolbar) findViewById(R.id.main_Toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.main_drawerLayout);
        mToolBar.inflateMenu(R.menu.zhihu_menu);
        mToolBar.setTitle("知乎日报");
        mToolBar.setNavigationIcon(R.mipmap.ic_drawer_home);
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolBar.setBackgroundColor(getResources().getColor( mIsLight? R.color.light_toolbar:R.color.dark_toolbar  ));
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.open,R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.main_swipe);
        mSwipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipe.setRefreshing(false);
            }
        });
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    //点击切换主题
                    case R.id.action_night:
                       mIsLight=!mIsLight;
                        SPUtil.newInstance(MainActivity.this).saveBoolean("isLight",mIsLight);
                        if (curFragment=="latest"){
                            ((MainFragment)getSupportFragmentManager().findFragmentByTag("latest")).updateTheme();
                        }else {
                            ((NewsFragment)getSupportFragmentManager().findFragmentByTag("news")).updateTheme();
                        }
                        ((MenuFragment)getSupportFragmentManager().findFragmentById(R.id.main_menuFragment)).updateTheme();
                        mToolBar.setBackgroundColor(getResources().getColor( mIsLight? R.color.light_toolbar:R.color.dark_toolbar  ));
                        break;
                    case R.id.action_setting:
                        Snackbar.make(mContent,"点击设置选项",Snackbar.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().
                replace(R.id.main_content, new MainFragment(), "latest").
                commit();
        curFragment="latest";

    }



    public static void setSwipeRefreshEnable(boolean enable){
        mSwipe.setEnabled(enable);
    }
    public void setToolBarTitle(String text){
        mToolBar.setTitle(text);
    }

    public  static void closeMenu(){
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else {
            long mSecondTime = System.currentTimeMillis();
            if (mSecondTime - mFirstTime > 2000) {
                Snackbar sb = Snackbar.make(mContent, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                sb.show();
                mFirstTime = mSecondTime;
            } else {
                finish();
            }
        }


}public CacheDBHelper getCacheDBHelper(){
        return mDbHelper;
    }
public static void setCurFragment(){
    curFragment="latest";
}
}

