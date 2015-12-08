
package com.codezyw.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * 位置服务工具类<br/>
 * 注意：需要添加权限&lt;uses-permission
 * android:name="android.permission.ACCESS_FINE_LOCATION"/&gt; <br/>
 * &lt;uses-permission
 * android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS">
 * </uses-permission>
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class LocationHelper {
    public static final String TAG = "LocationHelper";

    private static LocationManager locationManager;

    private static LocationListener network_listener;

    @SuppressWarnings("unused")
    private static LocationListener gps_listener;

    private static final int CHECK_INTERVAL = 1000 * 30;

    private static Location currentLocation;

    private LocationHelper(Context ctx) {
    }

    public interface LocationCallback {
        public void onLocation(Location location);
    }

    /**
     * 获取当前的位置信息
     * 
     * @param ctx
     * @return
     */
    public static void getLocation(Context ctx, LocationCallback callback) {
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        registerLocationListener(callback);
    }

    /**
     * @param ctx
     * @return
     */
    @Deprecated
    public static Location getLocation(Context ctx) {
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 查询精度：高
        criteria.setAltitudeRequired(false); // 是否查询海拨：否
        criteria.setBearingRequired(false); // 是否查询方位角 : 否
        criteria.setCostAllowed(false); // 是否允许付费：否
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 电量要求低
        // 获得当前的位置提供者
        String provider = locationManager.getBestProvider(criteria, true);
        // 获得当前的位置
        Location location = locationManager.getLastKnownLocation(provider);

        // 经度
        // location.getLongitude();
        // 纬度
        // location.getLatitude();
        Log.d(TAG, location.getProvider());

        return location;
    }

    // 一般来说NETWORK得到的位置精度一般在500-1000米，GPS得到的精度一般在5-50米
    private static void registerLocationListener(LocationCallback callback) {
        network_listener = new MyLocationListner(callback);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, network_listener);

        // gps_listener = new MyLocationListner(callback);
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        // 6000, 0, gps_listener);
    }

    /**
     * 打开GPS
     * 
     * @param ctx
     */
    public static void openGPS(Context ctx) {
        if (isGPSEnable(ctx)) {
            toggleGPS(ctx);
        }
    }

    /**
     * 切换GPS的开关状态
     * 
     * @param ctx
     */
    public static void toggleGPS(Context ctx) {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(ctx, 0, gpsIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断GPS是否可用，支持Android2.2以前的版本
     * 
     * @param ctx
     * @return
     */
    public static boolean isGPSEnable(Context ctx) {
        // 2.2以后版本可以直接使用以下这行代码来判断
        // boolean flag =
        // android.provider.Settings.Secure.isLocationProviderEnabled(ctx.getContentResolver(),
        // Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        // String str = Settings.System.getString(getContentResolver(),
        // Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        String str = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        Log.d(TAG, str);
        if (str != null) {
            return str.contains("gps");
        } else {
            return false;
        }
    }

    private static class MyLocationListner implements LocationListener {
        private LocationCallback callback;

        public MyLocationListner(LocationCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the location provider.
            Log.v(TAG, "当前定位服务提供者:" + location.getProvider());

            if (isBetterLocation(location, currentLocation)) {
                Log.v(TAG, "当前location是最好的");
                currentLocation = location;
            } else {
                Log.v(TAG, "当前location不是最好的");
            }

            showLocation(currentLocation);

            callback.onLocation(currentLocation);

            // if(LocationManager.NETWORK_PROVIDER.equals(location.getProvider())){
            locationManager.removeUpdates(this);
            // }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private static void showLocation(Location location) {
        // 纬度
        Log.v(TAG, "Latitude:" + location.getLatitude());
        // 经度
        Log.v(TAG, "Longitude:" + location.getLongitude());
        // 精确度
        Log.v(TAG, "Accuracy:" + location.getAccuracy());
    }

    protected static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > CHECK_INTERVAL;
        boolean isSignificantlyOlder = timeDelta < -CHECK_INTERVAL;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location,
        // use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must
            // be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    /**
     * <a href=
     * "http://developer.baidu.com/map/index.php?title=lbscloud/api/appendix"
     * >http://developer.baidu.com/map/index.php?title=lbscloud/api/appendix</a>
     * 
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static void SnCal() throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        // 计算sn跟参数对出现顺序有关，所以用LinkedHashMap保存<key,value>，此方法适用于get请求，如果是为发送post请求的url生成签名，请保证参数对按照key的字母顺序依次放入Map。以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("address", "百度大厦");
        paramsMap.put("output", "json");
        paramsMap.put("ak", "yourak");

        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "yoursk");

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        System.out.println(MD5(tempStr));
    }

    /**
     * 对Map内所有value作utf8编码，拼接返回结果
     * 
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String toQueryString(Map<String, String> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Entry<String, String> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    /**
     * 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
     * 
     * @param md5
     * @return
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
