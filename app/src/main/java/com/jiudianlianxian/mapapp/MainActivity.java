package com.jiudianlianxian.mapapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;


public class MainActivity extends AppCompatActivity {

    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    CheckBox cbNormal,cbSatellite,cbNone,cbLocationNormal,cbLocationFollowing,cbLocationCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        init();


    }

    /**
     * 初始化控件
     */
    private void init() {

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        cbNormal = (CheckBox) findViewById(R.id.cb_activity_main_normal);  //默认状态
        cbSatellite =  (CheckBox) findViewById(R.id.cb_activity_main_satellite);  //卫星地图
        cbNone =  (CheckBox) findViewById(R.id.cb_activity_main_none);  //空白地图

        setMapType();
        cbLocationNormal = (CheckBox) findViewById(R.id.cb_activity_main_location_normal);  //默认状态
        cbLocationFollowing =  (CheckBox) findViewById(R.id.cb_activity_main_location_following);  //跟随状态
        cbLocationCompass =  (CheckBox) findViewById(R.id.cb_activity_main_location_compass);  //罗盘态
        setLocationType();


    }

    /**
     * 地图定位类型
     */
    private void setLocationType() {
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//定位跟随态
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;   //默认为 LocationMode.NORMAL 普通态
//        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;  //定位罗盘态

        //自定义定位图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        //自定义精度圈填充颜色
        int accuracyCircleFillColor = 0xAAFFFF88;//自定义精度圈填充颜色
        //自定义精度圈边框颜色
        int accuracyCircleStrokeColor = 0xAA00FF00;//自定义精度圈边框颜色
        //定位精度圈大小 ，是根据当前定位精度自动控制的，无法手动控制大小。精度圈越小，代表当前定位精度越高；反之圈越大，代表当前定位精度越低。

        //定位指针方向:定位指针朝向，是通过获取手机系统陀螺仪数据，控制定位指针的方向，需要开发者自己实现，并不在地图实现范畴。
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        //定位的频次自定义:定位的频次需要开发者自己设置 取定位的时间间隔，设置取定位坐标属于定位SDK范畴。

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 构造定位数据
        BDLocation location = new BDLocation();
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_geo);
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);

        // 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
    }

    /**
     * 设置地图类型
     */
    private void setMapType() {
        cbNormal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
            }
        });
        cbSatellite.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        cbNone.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }



}
