
package com.codezyw.common;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.codezyw.common.HttpPostAsyncTask.PostListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AppBackupFragment extends BaseFragment {
    private LinearLayout mRootView;
    private Button mButtonBackUp;
    private ConvenientBanner<String> mConvenientBanner;
    private BackupAsyncTask mBackupAsyncTast;
    private final static String[] IMAGE_URL = {
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    /**
     * 必须有无参构造函数，否则影响状态恢复
     */
    public AppBackupFragment() {
    }

    public static AppBackupFragment newInstance(Bundle bundle) {
        AppBackupFragment f = new AppBackupFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        android.util.Log.i("zyw", "AppBackupFragment hashcode=" + AppBackupFragment.this);
        mRootView = new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mRootView.setLayoutParams(lp);
        mRootView.setOrientation(LinearLayout.VERTICAL);

        mConvenientBanner = new ConvenientBanner<String>(getActivity());
        LinearLayout.LayoutParams lpBanner = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBanner.height = (int) UnitHelper.dp2px(getActivity(), 200);
        mRootView.addView(mConvenientBanner, lpBanner);

        mButtonBackUp = new Button(getActivity());
        LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mButtonBackUp.setText("立即备份到/BackupApp");
        lpButton.bottomMargin = (int) UnitHelper.dp2px(getActivity(), 20);
        lpButton.topMargin = (int) UnitHelper.dp2px(getActivity(), 100);
        mRootView.addView(mButtonBackUp, lpButton);

        mButtonBackUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackupAsyncTast != null) {
                    return;
                }
                mBackupAsyncTast = new BackupAsyncTask(getActivity(), new PostListener() {

                    @Override
                    public void onProgressUpdate(int progress) {
                    }

                    @Override
                    public void onPreExecute() {
                    }

                    @Override
                    public void onPostExecute(String result) {
                        mBackupAsyncTast = null;
                    }

                    @Override
                    public void onCancelled(String result) {
                        mBackupAsyncTast = null;
                    }
                });
                mBackupAsyncTast.execute(true);
            }
        });
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable
    Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initImageLoader();
        // 网络加载例子
        mConvenientBanner.setPages(new
                CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, Arrays.asList(IMAGE_URL));
    }

    @Override
    public void onDestroy() {
        UIHelper.dismissProgressDialog();
        if (mBackupAsyncTast != null) {
            mBackupAsyncTast.cancel(true);
        }
        super.onDestroy();
    }

    /**
     * 初始化网络图片缓存库
     */
    private void initImageLoader() {
        if (ImageLoader.getInstance().isInited()) {
            return;
        }
        // 网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
        private ImageView mImageView;

        /**
         * 你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
         */
        @Override
        public View createView(Context context) {
            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return mImageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            mImageView.setImageDrawable(new ColorDrawable(Color.DKGRAY));
            ImageLoader.getInstance().displayImage(data, mImageView);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 点击事件
                    Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
