
package com.bigkoo.convenientbannerdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.codezyw.common.UnitHelper;
import com.codezyw.widget.banner.FlipBanner;
import com.codezyw.widget.banner.LoopIndicator;
import com.codezyw.widget.banner.RecyclingPagerAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Sai on 15/8/13. 这个是RecyclerView配合ConvenientBanner作为header的例子 有issue（ @OVERSKY2003
 * 同学）反馈说RecyclerView刷新会出现空白图片，于是写了这个例子进行测试，也提供给对RecyclerView使用不熟悉的开发者进行参考吧。
 */
public class RecyclerViewDemo extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mListView;
    private ArrayList<String> mRecyclerViewHFAdapterData = new ArrayList<String>();
    private RecyclerViewHFAdapter mRecyclerViewHFAdapter;
    private FlipBanner<String> mFlipBanner;
    private Context mContext = RecyclerViewDemo.this;
    private List<String> mNetWorkDataList;
    private static final String[] IMAGES = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        init();
        initEvents();
    }

    private void initViews() {
        setContentView(R.layout.acitvity_adrecyclerviewdemo);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mFlipBanner = new FlipBanner<String>(mContext);
        // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        mFlipBanner.setIndicatorResource(
                R.drawable.ic_page_indicator_focused, R.drawable.ic_page_indicator
                );
        // 设置指示器的方向
        mFlipBanner.getIndicator().setIndicatorAlign(LoopIndicator.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        mFlipBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) UnitHelper.dp2px(mContext, 200)));
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mFlipBanner.getIndicator().getLayoutParams();
        layoutParams.leftMargin = 20;
        layoutParams.topMargin = 20;
        layoutParams.rightMargin = 20;
        layoutParams.bottomMargin = 20;
        mListView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void init() {
        initImageLoader();

        mListView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mListView.setLayoutManager(layoutManager);
        mRecyclerViewHFAdapter = new RecyclerViewHFAdapter(mRecyclerViewHFAdapterData);
        mListView.setAdapter(mRecyclerViewHFAdapter);

        mNetWorkDataList = Arrays.asList(IMAGES);
        mFlipBanner.setViewPagerData(new NetworkLoopPageAdapter(), mNetWorkDataList);

        mRecyclerViewHFAdapter.addHeader(mFlipBanner);
        loadTestDatas();
    }

    private void initEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    // 初始化网络图片缓存库
    private void initImageLoader() {
        if (ImageLoader.getInstance().isInited()) {
            return;
        }
        // 网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /*
     * 加入测试Views
     */
    private void loadTestDatas() {

        mRecyclerViewHFAdapterData.add("test＝＝＝＝＝＝＝＝＝＝＝");
        mRecyclerViewHFAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 开始自动翻页
        mFlipBanner.startFlipping(5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 停止翻页
        mFlipBanner.stopFlipping();
    }

    @Override
    public void onRefresh() {

        // mDatas.add("dsafdsf");
        // adapter.notifyDataSetChanged();
        // 跟上面注释的效果一样
        mRecyclerViewHFAdapter.addData("onRefresh  ===test========");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class NetworkLoopPageAdapter extends RecyclingPagerAdapter {
        public NetworkLoopPageAdapter() {
        }

        @Override
        public View getView(final int position, View view, ViewGroup container) {
            ImageView image = null;
            if (view == null) {
                image = new ImageView(mContext);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                view = image;
            } else {
                image = (ImageView) view;
            }
            image.setImageResource(R.drawable.ic_default_adimage);
            if (mNetWorkDataList != null && !mNetWorkDataList.isEmpty()) {
                ImageLoader.getInstance().displayImage(mNetWorkDataList.get(position), image);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 点击事件
                        Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return view;
        }

        @Override
        public int getCount() {
            if (mNetWorkDataList == null) {
                return 0;
            }
            return mNetWorkDataList.size();
        }
    }
}
