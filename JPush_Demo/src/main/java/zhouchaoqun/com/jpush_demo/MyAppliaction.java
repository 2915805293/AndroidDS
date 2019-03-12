package zhouchaoqun.com.jpush_demo;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Create by ZCQ on 2019/1/9.
 */
public class MyAppliaction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
