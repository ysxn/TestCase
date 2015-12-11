
package com.codezyw.freeparking;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.cloud.BoundSearchInfo;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchInfo;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.LocalSearchInfo;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.LatLngBounds.Builder;
import com.codezyw.common.DeviceHelper;
import com.codezyw.common.DownloadService;
import com.codezyw.common.HttpAPKBean;
import com.codezyw.common.HttpAdvBean;
import com.codezyw.common.HttpAdvBean.DataBean;
import com.codezyw.common.HttpHelper;
import com.codezyw.common.HttpPostAsyncTask.PostListener;
import com.codezyw.common.JsonHelper;
import com.codezyw.widget.banner.FlipBanner;
import com.codezyw.widget.banner.RecyclingPagerAdapter;

/**
 * TODO 代码保护,分享,
 */
public class CloudSearchActivity extends Activity implements CloudListener {
    private static final String TAG = "temp";
    private static final boolean DEBUG = Constant.DEBUG;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private int mPageIndex = 0;
    private final int mPageSize = 10;
    private FlipBanner<DataBean> mFlipBanner;
    private List<DataBean> mDataList;
    private long mBackPressed = 0L;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lbssearch);

        mFlipBanner = (FlipBanner) findViewById(R.id.banner);
        mFlipBanner.setVisibility(View.GONE);
        CloudManager.getInstance().init(CloudSearchActivity.this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(CloudSearchActivity.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
        localSearch();
        HttpHelper.sendStatistics(this, this.getPackageName() + " onCreate", null);
        HttpHelper.asyncHttpGet(HttpHelper.APK_UPDATE_URL, this, new PostListener() {

            @Override
            public void onProgressUpdate(int progress) {
            }

            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute(String result) {
                HttpAPKBean bean = JsonHelper.parseHttpAPKBeanByGson(result);
                if (bean != null && CloudSearchActivity.this.getPackageName().equals(bean.getPackage_name())) {
                    float oldVersion = DeviceHelper.getInstance().getVersionCode();
                    float newVersion = oldVersion;
                    try {
                        newVersion = Float.parseFloat(bean.getVersion_code().trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (oldVersion < newVersion) {
                        if (DownloadService.isDownloadComplete(CloudSearchActivity.this, bean.getUrl())) {
                            DownloadService.tryInstall(CloudSearchActivity.this, bean.getUrl());
                        } else {
                            DownloadService.startDownloadService(CloudSearchActivity.this, bean.getUrl(), true, true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(String result) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        // 开始自动翻页
        mFlipBanner.startFlipping(5000);
        HttpHelper.sendStatistics(this, this.getPackageName() + " onResume", null);
        HttpHelper.asyncHttpGet(HttpHelper.ADV_URL, this, new PostListener() {

            @Override
            public void onProgressUpdate(int progress) {
            }

            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute(String result) {
                HttpAdvBean bean = JsonHelper.parseHttpAdvBeanByGson(result);
                if (bean != null && bean.getData() != null && bean.getData().size() > 0) {
                    mDataList = bean.getData();
                    mFlipBanner.setViewPagerData(new LoopPageAdapter(), mDataList);
                    mFlipBanner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(String result) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        // 停止翻页
        mFlipBanner.stopFlipping();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        CloudManager.getInstance().destroy();
    }

    @Override
    public void onBackPressed() {
        long previous = mBackPressed;
        mBackPressed = SystemClock.elapsedRealtime();
        if (mBackPressed - previous < 3500) {
            finish();
        } else {
            Toast.makeText(this, "再按一下返回键退出应用.", Toast.LENGTH_SHORT).show();
        }
    }

    public void localSearch() {
        LocalSearchInfo info = new LocalSearchInfo();
        info.ak = Constant.ACCESS_KEY_SERVER;
        info.geoTableId = Constant.GEO_TABLE_ID;
        // info.tags = "免费停车";
        info.q = "";// "免费停车";
        info.region = "上海市";
        info.sn = "";
        info.pageIndex = mPageIndex;
        info.pageSize = mPageSize;
        CloudManager.getInstance().localSearch(info);
    }

    public void nearbySearch() {
        NearbySearchInfo info = new NearbySearchInfo();
        info.ak = Constant.ACCESS_KEY_SERVER;
        info.geoTableId = Constant.GEO_TABLE_ID;
        info.radius = 3000;
        info.location = "121.623889,31.220752";
        info.sn = "";
        CloudManager.getInstance().nearbySearch(info);
    }

    public void boundsSearch() {
        BoundSearchInfo info = new BoundSearchInfo();
        info.ak = Constant.ACCESS_KEY_SERVER;
        info.geoTableId = Constant.GEO_TABLE_ID;
        info.q = "";// "天安门";
        // 左下角和右上角的经纬度坐标点。2个点用;号分隔
        info.bound = "121.621016,31.219532;121.62549,31.226156";
        CloudManager.getInstance().boundSearch(info);
    }

    public void detailsSearch() {
        DetailSearchInfo info = new DetailSearchInfo();
        info.ak = Constant.ACCESS_KEY_SERVER;
        info.geoTableId = Constant.GEO_TABLE_ID;
        // info.uid = 18622266;
        CloudManager.getInstance().detailSearch(info);
    }

    public void onGetDetailSearchResult(DetailSearchResult result, int error) {
        if (result != null) {
            if (result.poiInfo != null) {
                Toast.makeText(CloudSearchActivity.this, result.poiInfo.title,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CloudSearchActivity.this,
                        "status:" + result.status, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onGetSearchResult(CloudSearchResult result, int error) {
        if (DEBUG) {
            Log.d(TAG, "onGetSearchResult, error: " + error);
        }
        if (result != null && result.poiList != null
                && result.poiList.size() > 0) {
            if (DEBUG) {
                Log.d(TAG, "onGetSearchResult, result.total=" + result.total + " mPageIndex=" + mPageIndex
                        + " result.size=" + result.size + " poiList.size="
                        + result.poiList.size());
            }
            if (mPageIndex == 0) {
                mBaiduMap.clear();
            }
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            LatLng ll;
            LatLngBounds.Builder builder = new Builder();
            for (CloudPoiInfo info : result.poiList) {
                ll = new LatLng(info.latitude, info.longitude);
                if (DEBUG) {
                    // Log.d(TAG, "onGetSearchResult, info = " + info.address + "\n" + info.title);
                }
                OverlayOptions oo = new MarkerOptions().icon(bd).position(ll).title(info.address + "\n" + info.title);
                mBaiduMap.addOverlay(oo);
                builder.include(ll);
            }
            LatLngBounds bounds = builder.build();
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);

            if (result.total > (mPageIndex * mPageSize + result.size)) {
                mPageIndex++;
                localSearch();
            }
        }
    }

    public class LoopPageAdapter extends RecyclingPagerAdapter {
        public LoopPageAdapter() {
            super();
        }

        @Override
        public View getView(final int position, View view, ViewGroup container) {
            TextView text = null;
            if (view == null) {
                text = new TextView(CloudSearchActivity.this);
                // image.setScaleType(ImageView.ScaleType.FIT_XY);
                view = text;
                text.setGravity(Gravity.CENTER);
                text.setTextColor(getResources().getColorStateList(R.drawable.text_color));
            } else {
                text = (TextView) view;
            }
            // image.setImageDrawable(new ColorDrawable(Color.DKGRAY));
            if (mDataList != null && !mDataList.isEmpty()) {
                // ImageLoader.getInstance().displayImage(mDataList.get(position), image);
                final String url = mDataList.get(position).getUrl();
                text.setText(mDataList.get(position).getContent());
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(url)) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
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
