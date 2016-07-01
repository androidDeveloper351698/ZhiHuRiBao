package com.example.dell.zhihu.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.dell.zhihu.Fragment.MainFragment;
import com.example.dell.zhihu.R;

public class MainActivity extends AppCompatActivity{

    private Toolbar mToolBar;
    private static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private  static SwipeRefreshLayout mSwipe;
    private long mFirstTime;
    private FrameLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mContent= (FrameLayout) findViewById(R.id.main_content);
       /* MenuFragment fragment=new MenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_menuFragment,fragment).commit();*/
        mToolBar= (Toolbar) findViewById(R.id.main_Toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.main_drawerLayout);
        mToolBar.inflateMenu(R.menu.zhihu_menu);
        mToolBar.setTitle("知乎日报");
        mToolBar.setNavigationIcon(R.mipmap.ic_drawer_home);
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
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

        getSupportFragmentManager().beginTransaction().
                replace(R.id.main_content, new MainFragment(), "latest").
                commit();

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

}

}
