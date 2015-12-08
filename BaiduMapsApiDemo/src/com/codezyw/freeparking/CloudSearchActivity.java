
package com.codezyw.freeparking;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class CloudSearchActivity extends Activity implements CloudListener {
    private static final String LTAG = "temp";
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_lbssearch);
        CloudManager.getInstance().init(CloudSearchActivity.this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        findViewById(R.id.localSearch).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocalSearchInfo info = new LocalSearchInfo();
                        info.ak = Constant.ACCESS_KEY_SERVER;// "B266f735e43ab207ec152deff44fec8b";
                        info.geoTableId = 128216;// 31869;
                        // info.tags = "免费停车";
                        info.q = "";// "免费停车";
                        info.region = "上海市";
                        info.sn = "";
                        CloudManager.getInstance().localSearch(info);
                    }
                });
        findViewById(R.id.nearbySearch).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        NearbySearchInfo info = new NearbySearchInfo();
                        info.ak = Constant.ACCESS_KEY_SERVER;// "D9ace96891048231e8777291cda45ca0";
                        info.geoTableId = 128216;// 32038;
                        info.radius = 3000;
                        info.location = "121.623889,31.220752";// "116.403689,39.914957";
                        info.sn = "";
                        CloudManager.getInstance().nearbySearch(info);
                    }
                });

        findViewById(R.id.boundsSearch).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        BoundSearchInfo info = new BoundSearchInfo();
                        info.ak = Constant.ACCESS_KEY_SERVER;// "B266f735e43ab207ec152deff44fec8b";
                        info.geoTableId = 128216;// 31869;
                        info.q = "";// "天安门";
                        // 左下角和右上角的经纬度坐标点。2个点用;号分隔
                        info.bound = "121.621016,31.219532;121.62549,31.226156";// "116.401663,39.913961;116.406529,39.917396";
                        CloudManager.getInstance().boundSearch(info);
                    }
                });
        findViewById(R.id.detailsSearch).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        DetailSearchInfo info = new DetailSearchInfo();
                        info.ak = Constant.ACCESS_KEY_SERVER;// "B266f735e43ab207ec152deff44fec8b";
                        info.geoTableId = 128216;// 31869;
                        // info.uid = 18622266;
                        CloudManager.getInstance().detailSearch(info);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        CloudManager.getInstance().destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
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
        Log.d(LTAG, "onGetSearchResult, error: " + error);
        if (result != null && result.poiList != null
                && result.poiList.size() > 0) {
            Log.d(LTAG, "onGetSearchResult, result length: " + result.poiList.size());
            mBaiduMap.clear();
            BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            LatLng ll;
            LatLngBounds.Builder builder = new Builder();
            for (CloudPoiInfo info : result.poiList) {
                ll = new LatLng(info.latitude, info.longitude);
                OverlayOptions oo = new MarkerOptions().icon(bd).position(ll).title(info.address + "\n" + info.title);
                mBaiduMap.addOverlay(oo);
                builder.include(ll);
            }
            LatLngBounds bounds = builder.build();
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);
            mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(CloudSearchActivity.this, marker.getTitle(), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }
    }
}
