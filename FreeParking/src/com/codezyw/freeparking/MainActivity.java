
package com.codezyw.freeparking;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.VersionInfo;

//假如用到位置提醒功能，需要import该类

public class MainActivity extends Activity {
    private Button mButton;
    private TextView mResult;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;

    /**
     * 1.LocationMode.Hight_Accuracy 高精度定位模式下，会同时使用GPS、Wifi和基站定位，返回的是当前条件下精度最好的定位结果<br>
     * 2.LocationMode.Battery_Saving 低功耗定位模式下，仅使用网络定位即Wifi和基站定位，返回的是当前条件下精度最好的网络定位结果<br>
     * 3.LocationMode.Device_Sensors 仅用设备定位模式下，只使用用户的GPS进行定位。这个模式下，由于GPS芯片锁定需要时间，首次定位速度会需要一定的时间
     * <p>
     * 
     * @see LocationMode.Hight_Accuracy
     * @see LocationMode.Battery_Saving
     * @see LocationMode.Device_Sensors
     */
    private LocationMode mLocationMode = LocationMode.Hight_Accuracy;
    /**
     * 1."gcj02" 国家测绘局标准<br>
     * 2."bd09ll" 百度经纬度标准<br>
     * 3."bd09" 百度墨卡托标准
     */
    private String mCoorType = "gcj02";

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            TextView text = (TextView) findViewById(R.id.text_Info);
            text.setTextColor(Color.RED);
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                text.setText("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
            } else if (s
                    .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                text.setText("key 验证成功! 功能可以正常使用");
            } else if (s
                    .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                text.setText("网络出错");
            }
        }
    }

    private SDKReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        TextView text = (TextView) findViewById(R.id.text_Info);
        text.setText("欢迎使用百度地图Android SDK v" + VersionInfo.getApiVersion());

        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        mResult = (TextView) findViewById(R.id.result);
        mButton = (Button) findViewById(R.id.location);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                initLocation();
                if ("开始定位".equals(mButton.getText())) {
                    // 定位SDK start之后会默认发起一次定位请求，开发者无须判断isStart并主动调用request
                    mLocationClient.start();
                    /**
                     * <pre>
                     * requestLocation: 
                     * 发起定位，异步获取当前位置。因为是异步的，所以立即返回，不会引起阻塞。定位结果在ReceiveListener的方法OnReceive方法的参数中返回。
                     * 需要注意：当定位SDK从定位依据判定，位置和上一次没发生变化，而且上一次定位结果可用时，则不会发起网络请求，而是返回上一次的定位结果。 返回值：
                     * 0：正常发起了定位。
                     * 1：服务没有启动。
                     * 2：没有监听函数。
                     * 6：请求间隔过短。 前后两次请求定位时间间隔不能小于1000ms。
                     * </pre>
                     */
                    int r = mLocationClient.requestLocation();
                    String s = (r == 0 ? "正常发起了定位" : (r == 1 ? "服务没有启动" : (r == 2 ? "没有监听函数" : (r == 6 ? "请求间隔过短" : "UNKNOWN"))));
                    mButton.setText("停止定位 r=" + r + " " + s);
                } else {
                    mLocationClient.stop();
                    mButton.setText("开始定位");
                }

            }
        });

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        mLocationClient.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        // 取消监听 SDK 广播
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * <pre>
     * 设置定位参数
     *  设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS等等。
     *  高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
     *  低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）
     *  仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
     *  eg：
     *      LocationClientOption option = new LocationClientOption();
     *      option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
     *      option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
     *      option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
     *      option.setIsNeedAddress(true);//返回的定位结果包含地址信息
     *      option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
     *      mLocClient.setLocOption(option);
     * 
     * LocationClientOption类，该类用来设置定位SDK的定位方式，具体方法如下：
     * 设置定位模式：
     *  //Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
     *  public void setLocationMode(LocationMode mode)
     * 
     * 设置打开GPS：
     *  setOpenGps( boolean )
     * 设置是否打开gps，使用gps前提是用户硬件打开gps。默认是不打开gps的。
     * 
     * 设置是否需要设备方向信息：
     *  //在网络定位中，获取手机机头所指的方向
     *  public void setNeedDeviceDirect(boolean)
     * 
     * 设置是否需要地址信息：
     *  使用setIsNeedAddress(boolean)
     *  
     * 设置是否要返回地址信息，默认为无地址信息。
     *  public void setAddrType ( String )
     *  String 值为 all时，表示返回地址信息，其他值都表示不返回地址信息。
     * 
     * 设置坐标类型：
     *  设置返回值的坐标类型。
     *  public void setCoorType ( String )
     *  我们支持返回若干种坐标系，包括国测局坐标系、百度坐标系，需要更多坐标系请联系我们，需要深度合作。
     *  因此需要在请求时指定类型，如果不指定，默认返回百度坐标系。注意当仅输入IP时，不会返回坐标。
     *  目前这些参数的代码为
     *  返回国测局经纬度坐标系 coor=gcj02
     *  返回百度墨卡托坐标系 coor=bd09
     *  返回百度经纬度坐标系 coor=bd09ll
     *  百度手机地图对外接口中的坐标系默认是bd09ll，如果配合百度地图产品的话，需要注意坐标系对应问题。
     *  有关坐标系的更多问题，请看常见问题
     * 
     * 设置产品线名称：
     *  public void setProdName ( String )
     *  设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
     * 
     * 设置定位时间间隔：
     *  public void setScanSpan ( int )     //设置定时定位的时间间隔。单位ms
     *  说明：
     *  当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。
     *  调用requestLocation( )后，每隔设定的时间，定位SDK就会进行一次定位。
     *  如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
     *  定时定位时，调用一次requestLocation，会定时监听到定位结果。
     *  当不设此项，或者所设的整数值小于1000（ms）时，采用一次定位模式。每调用一次requestLocation( )，定位SDK会发起一次定位。请求定位与监听结果一一对应。
     *  设定了定时定位后，可以热切换成一次定位，需要重新设置时间间隔小于1000（ms）即可。
     *  locationClient对象stop后，将不再进行定位。
     *  如果设定了定时定位模式后，多次调用requestLocation（），则是每隔一段时间进行一次定位，同时额外的定位请求也会进行定位，但频率不会超过1秒一次。
     * 
     * 设置位置提醒接口：
     *  public void registerNotify( BDNotifyListener mNotify )
     *  LocationClient注册位置提醒监听事件
     *  public void removeNotifyEvent( BDNotifyListener mNotify )
     *  LocationClient取消位置提醒监听事件
     * 
     * 发起定位请求
     *  发起定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
     *  if (mLocClient != null && mLocClient.isStarted())
     *      mLocClient.requestLocation();
     *  else 
     *      Log.d("LocSDK4", "locClient is null or not started");
     * 
     * 发起离线定位请求
     *  发起离线定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
     *  getLocTypte = BDLocation.TypteOfflineLocation || BDLocation.TypeOfflineLocationFail
     * 
     * 表示是离线定位请求返回的定位结果
     *  if (mLocClient != null && mLocClient.isStarted())
     *      mLocClient.requestOfflineLocation();
     * 
     * 位置提醒使用
     *  位置提醒最多提醒3次，3次过后将不再提醒。 假如需要再次提醒，或者要修改提醒点坐标，都可通过函数SetNotifyLocation()来实现。
     *  //位置提醒相关代码
     *  mNotifyer = new NotifyLister();
     *  mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,3000,"gps");//4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
     *  mLocationClient.registerNotify(mNotifyer);
     *  //注册位置提醒监听事件后，可以通过SetNotifyLocation 来修改位置提醒设置，修改后立刻生效。
     *  //BDNotifyListner实现
     *      public class NotifyLister extends BDNotifyListener{
     *          public void onNotify(BDLocation mlocation, float distance){
     *              mVibrator01.vibrate(1000);//振动提醒已到设定位置附近
     *          }
     *      }
     *  //取消位置提醒
     *  mLocationClient.removeNotifyEvent(mNotifyer);
     * </pre>
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(mLocationMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(mCoorType);// 可选，默认gcj02，设置返回的定位结果坐标系，
        int span = 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        mLocationClient.setLocOption(option);
    }

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        /**
         * <pre>
         * BDLocation类，封装了定位SDK的定位结果，在BDLocationListener的onReceive方法中获取。通过该类用户可以获取error code，位置的坐标，精度半径等信息。具体方法如下：
         * 
         * 获取error code：
         *  public int getLocType ( )
         *      返回值：
         *      61 ： GPS定位结果
         *      62 ： 扫描整合定位依据失败。此时定位结果无效。
         *      63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
         *      65 ： 定位缓存的结果。
         *      66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
         *      67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
         *      68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
         *      161： 表示网络定位结果
         *      162~167： 服务端定位失败
         *      502：key参数错误
         *      505：key不存在或者非法
         *      601：key服务被开发者自己禁用
         *      602：key mcode不匹配
         *      501～700：key验证失败
         * 如果不能定位，请记住这个返回值，并到我们的hi群或者贴吧中交流。若返回值是162~167，请发送邮件至mapapi@baidu.com反馈。
         * 
         * 获取经纬度坐标：
         *  public double getLatitude ( )     //获取维度
         *  public double getLongitude ( )    //获取经度
         * 获取定位的坐标。坐标的类型在setLocationClientOption方法中设定。
         * 
         * 获取定位精度：
         *  public boolean hasRadius ( )    //判断是否有定位精度半径
         *  public float getRadius ( )    //获取定位精度半径，单位是米
         * 
         * 获取文字描述的地址(反地理编码)：
         *  public String getAddrStr ( )     //获取反地理编码
         * 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
         * 自定位SDK2.6版本之后，支持获取省/市/区分级地理信息：
         *  public String getProvince ( )     //获取省份信息
         *  public String getCity ( )     //获取城市信息
         *  public String getDistrict ( )     //获取区县信息
         * 
         * 获取手机方向信息：
         *  public float getDirection() //获得手机方向，范围【0-360】，手机上部正朝向北的方向为0°方向
         * </pre>
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            mResult.setText(sb.toString());
            // mLocationClient.setEnableGpsRealTimeTransfer(true);
        }

    }
}
