package com.example.dell.zhihu.View;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.zhihu.Activity.MainActivity;
import com.example.dell.zhihu.Model.Latest;
import com.example.dell.zhihu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DELL on 2016/6/25.
 */
public class Kanner extends FrameLayout implements View.OnClickListener {
    //存放ImageView和TextView的View
    private List<View> mViews;
    //用来存放是否被选中的原点的
    private LinearLayout mLl_dots;
    //显示原点的ImageView
    private List<ImageView> mDots;
    //存放图片的ViewPager
    private ViewPager mViewPager;

    private List<Latest.TopStoriesBean> mTopStoriesBeans;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    private Context mContext;

    private int mDelayTime;
    private int mCurrentItem;

    //是否自动滑动
    private boolean mIsAutoPaly;

    private Handler mHandler = new Handler();

    private OnItemClickListener mOnItemClickListener;

    private MyPagerAdapter mAdapter;

    private ImageView mShow;

    public Kanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public Kanner(Context context) {
        super(context);

    }

    public Kanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        this.mContext = context;
        this.mTopStoriesBeans = new ArrayList<>();
        mDelayTime = 2000;
        initView();
    }


    private void initView() {
        mViews = new ArrayList<>();
        mDots = new ArrayList<>();
    }

    //设置显示TopStories
    public void setTopBeans(List<Latest.TopStoriesBean> topStories) {
        mTopStoriesBeans = topStories;
        reset();
    }

    //设置显示后清除掉现实的，重新获取
    private void reset() {
        mViews.clear();
        initUI();
        mAdapter.notifyDataSetChanged();

    }

    private void initUI() {
        //得到整个视图，分别放置ViewPager和原点
        View mViewAll = LayoutInflater.from(mContext).inflate(
                R.layout.kanner_layout, this, true);
        mViewPager = (ViewPager) mViewAll.findViewById(R.id.kanner_ViewPager);
        mLl_dots = (LinearLayout) mViewAll.findViewById(R.id.kanner_ll_dots);
        mLl_dots.removeAllViews();
        int len = mTopStoriesBeans.size();

        //初始化显示原点的ImageView
        for (int i = 0; i < len; i++) {
            ImageView iv_dot = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            //将每个ImageView都添加到显示所有原点的LinearLayout
            mLl_dots.addView(iv_dot);

            //将所有显示原点的ImageView添加到圆点集合中
            mDots.add(iv_dot);
        }

        //初始化显示图片和标题的ImageView和TextView，为了使最后一张与第一张之间也可以转换，因此多加了两张在最前面与最后面

        for (int i = 0; i <= len + 1; i++) {
            View mViewContent = LayoutInflater.from(mContext).inflate(R.layout.kanner_content_layout, null);
            ImageView mContentImageView = (ImageView) mViewContent.findViewById(R.id.kanner_content_IV);
            TextView mContentTextView = (TextView) mViewContent.findViewById(R.id.kanner_content_title_TV);
            mContentTextView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            mContentTextView.getBackground().setAlpha(150);
            if (i == 0) {
                //最前面的显示最后一张视图
                mImageLoader.displayImage(mTopStoriesBeans.get(len - 1).getImage(), mContentImageView, mOptions);
                mContentTextView.setText(mTopStoriesBeans.get(len - 1).getTitle());
            } else if (i == len + 1) {
                //最后面的显示第一张视图
                mImageLoader.displayImage(mTopStoriesBeans.get(0).getImage(), mContentImageView, mOptions);
                mContentTextView.setText(mTopStoriesBeans.get(0).getTitle());
            } else {
                mImageLoader.displayImage(mTopStoriesBeans.get(i - 1).getImage(), mContentImageView, mOptions);
                mContentTextView.setText(mTopStoriesBeans.get(i - 1).getTitle());
            }
            mViewContent.setOnClickListener(this);
            //将这些内容视图添加到集合中

            mViews.add(mViewContent);
        }

            //给ViewPager设置适配器
            mAdapter = new MyPagerAdapter();
            mViewPager.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mViewPager.setFocusable(true);
            //设置第0(1-1)个
            mViewPager.setCurrentItem(1);
            mCurrentItem = 1;
            mViewPager.addOnPageChangeListener(new OnMyPageChangeListener());
            startPlay();
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        MainActivity.setSwipeRefreshEnable(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        MainActivity.setSwipeRefreshEnable(true);
                        break;
                }
                return false;
            }
        });
    }

    private void startPlay() {
        mIsAutoPaly = true;
        mHandler.postDelayed(task, 3000);

    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (mIsAutoPaly) {
                mCurrentItem = mCurrentItem % (mTopStoriesBeans.size() + 1) + 1;
                if (mCurrentItem == 1) {
                    //即mCurrentItem=5时，立马显示到第一个，没有延迟
                    mViewPager.setCurrentItem(mCurrentItem, false);
                    mHandler.post(task);
                } else {
                    mViewPager.setCurrentItem(mCurrentItem);
                    mHandler.postDelayed(task, 5000);
                }
            } else {
                mHandler.postDelayed(task, 5000);
            }
        }
    };


    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));

            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));

        }
    }

    //ViewPager的页面变化事件
    class OnMyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        //正在滑动的时候掉用，滑动结束之前会一直被回调
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        //滑动结束后调用，position是当前的页面
        public void onPageSelected(int position) {
            for (int i = 0; i < mDots.size(); i++) {
                //由于最开始多加了一个ImageView，所以当前位置-1即为对应的dot位置
                if (i == position - 1) {
                    mDots.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    mDots.get(i).setImageResource(R.drawable.dot_blur);
                }
            }
        }


        @Override
        //滑动状态改变时调用,有三种状态:1(正在滑动) 2（滑动完毕） 0(什么都没做)，调用顺序即为1 2 0
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 1:
                    mIsAutoPaly = false;
                    break;
                case 2:
                    mIsAutoPaly = true;
                    break;
                case 0:
                    //如果滑到第0个，直接跳到倒数第二个
                    if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(mTopStoriesBeans.size(), false);
                    } else if (mViewPager.getCurrentItem() == mTopStoriesBeans.size() + 1) {
                        //如果滑倒最后一个，直接跳到第一个
                        mViewPager.setCurrentItem(1, false);
                    }
                    mCurrentItem = mViewPager.getCurrentItem();
                    mIsAutoPaly = true;
                    break;

            }
        }

    }

    public interface OnItemClickListener {
        public void click(View view, Latest.TopStoriesBean bean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            Latest.TopStoriesBean topBean = mTopStoriesBeans.get(mViewPager.getCurrentItem() - 1);
            mOnItemClickListener.click(v, topBean);
        }
    }
}
