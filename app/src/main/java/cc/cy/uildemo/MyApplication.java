package cc.cy.uildemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cc.cy.uildemo.utils.FrescoUtil;

/**
 * Created by zcy on 2018/5/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco使用需要初始化
        FrescoUtil.init(this);
    }
}
