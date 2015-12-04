
package com.bigkoo.convenientbannerdemo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.bigkoo.convenientbanner.FlipBanner;
import com.bigkoo.convenientbanner.FlipBanner.Transformer;
import com.bigkoo.convenientbanner.LoopIndicator;
import com.bigkoo.convenientbanner.RecyclingPagerAdapter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    @SuppressWarnings("rawtypes")
    private FlipBanner mFlipBanner;// 顶部广告栏控件
    private ArrayList<Integer> mDataList = new ArrayList<Integer>();
    private List<String> mNetWorkDataList;
    private ListView mListView;
    private Context mContext = MainActivity.this;
    private ArrayAdapter<String> mTransformerArrayAdapter;
    private ArrayList<String> mTransformerList = new ArrayList<String>();
    private String[] IMAGES = {
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
        setContentView(R.layout.activity_main);
        initViews();
        init();
    }

    private void initViews() {
        mFlipBanner = (FlipBanner) findViewById(R.id.convenientBanner);
        mListView = (ListView) findViewById(R.id.listView);
        mTransformerArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_transformer, mTransformerList);
        mListView.setAdapter(mTransformerArrayAdapter);
        mListView.setOnItemClickListener(this);
    }

    @SuppressWarnings("unused")
    private void init() {
        initImageLoader();
        loadTestDatas();
        // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        mFlipBanner.setIndicatorResource(
                R.drawable.ic_page_indicator_focused, R.drawable.ic_page_indicator
                );
        // 设置指示器的方向
        mFlipBanner.getIndicator().setIndicatorAlign(LoopIndicator.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mFlipBanner.getIndicator().getLayoutParams();
        layoutParams.leftMargin = 20;
        layoutParams.topMargin = 20;
        layoutParams.rightMargin = 20;
        layoutParams.bottomMargin = 20;
        // 本地图片例子
        mFlipBanner.setViewPagerData(new LoopPageAdapter(), mDataList)
                // 设置翻页的效果，不需要翻页效果可用不设
                .setPageTransformer(Transformer.DefaultTransformer);
        // 设置不能手动影响
        if (false) {
            mFlipBanner.getViewPager().setCanScrollByTouch(false);
        }

        // 网络加载例子
        if (false) {
            mNetWorkDataList = Arrays.asList(IMAGES);
            mFlipBanner.setViewPagerData(new NetworkLoopPageAdapter(), mNetWorkDataList);
        }
    }

    /**
     * 初始化网络图片缓存库
     */
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

    /**
     * 加入测试Views
     */
    private void loadTestDatas() {
        // 本地图片集合
        for (int position = 0; position < 7; position++) {
            mDataList.add(getResId("ic_test_" + position, R.drawable.class));
        }
        // 各种翻页效果
        mTransformerList.add(Transformer.DefaultTransformer.getClassName());
        mTransformerList.add(Transformer.AccordionTransformer.getClassName());
        mTransformerList.add(Transformer.BackgroundToForegroundTransformer.getClassName());
        mTransformerList.add(Transformer.CubeInTransformer.getClassName());
        mTransformerList.add(Transformer.CubeOutTransformer.getClassName());
        mTransformerList.add(Transformer.DepthPageTransformer.getClassName());
        mTransformerList.add(Transformer.FlipHorizontalTransformer.getClassName());
        mTransformerList.add(Transformer.FlipVerticalTransformer.getClassName());
        mTransformerList.add(Transformer.ForegroundToBackgroundTransformer.getClassName());
        mTransformerList.add(Transformer.RotateDownTransformer.getClassName());
        mTransformerList.add(Transformer.RotateUpTransformer.getClassName());
        mTransformerList.add(Transformer.StackTransformer.getClassName());
        mTransformerList.add(Transformer.ZoomInTransformer.getClassName());
        mTransformerList.add(Transformer.ZoomOutTranformer.getClassName());
        mTransformerList.add("跳转下一个界面");

        mTransformerArrayAdapter.notifyDataSetChanged();
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     * 
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
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

    /**
     * 点击切换效果
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String name = mTransformerList.get(position);
        if ("跳转下一个界面".equals(name)) {
            startActivity(new Intent(mContext, RecyclerViewDemo.class));
        } else {
            Transformer transformer = Transformer.valueOf(name);
            mFlipBanner.setPageTransformer(transformer);
        }
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
            if (mDataList == null) {
                return 0;
            }
            return mDataList.size();
        }
    }

    public class LoopPageAdapter extends RecyclingPagerAdapter {
        public LoopPageAdapter() {
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
            image.setImageDrawable(new ColorDrawable(Color.DKGRAY));
            if (mDataList != null && !mDataList.isEmpty()) {
                image.setImageResource(mDataList.get(position));
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
            if (mDataList == null) {
                return 0;
            }
            return mDataList.size();
        }
    }
}
