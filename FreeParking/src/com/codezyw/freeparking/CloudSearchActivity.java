
package com.codezyw.freeparking;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
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

/**
 * TODO 下载更新，崩溃报告，广告json，意见反馈，代码保护--(apk运行md5检查),应用统计,屏幕常亮,分享,
 */
public class CloudSearchActivity extends Activity implements CloudListener {
    private static final String TAG = "temp";
    private static final boolean DEBUG = Constant.DEBUG;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private int mPageIndex = 0;
    private final int mPageSize = 10;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lbssearch);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        CloudManager.getInstance().destroy();
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
                    Log.d(TAG, "onGetSearchResult, info = " + info.address + "\n" + info.title);
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
}
