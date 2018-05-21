package cc.cy.uildemo.utils.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import cc.cy.uildemo.R;

/**
 * Created by zcy on 2018/5/18.
 */

public class GlideUtil {
    /**
     * 1.使用简单
     * 2.多数据源
     * 3.支持GIF、webp、video(本地)
     * 4.默认的内存和磁盘缓存 skipMemoryCache可跳过内存缓存  diskCacheStrategy(DiskCacheStrategy.NONE)可跳过磁盘缓存
     * 5.支持优先级
     *
     * @param context
     * @param url
     * @param img
     */
    public static void load(Context context, String url, ImageView img) {

        Glide.with(context)//需要上下文，可以是Activity、Fragment绑定生命周期，暂停或恢复加载
                .load(url)//多重载方法，加载不同数据源
                .thumbnail(0.2f)//缩略图0.2图片20%大小
                .apply(sRequestOptions)//4.0以上需要使用此方法来配置图片
                .into(img);

        //需要注解支持 并在manifest注册
        /*GlideApp.with(context)
                .load(url)
                .thumbnail(0.5f)
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(img);*/
    }

    private static RequestOptions sRequestOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            //.diskCacheStrategy(DiskCacheStrategy.NONE)
            //.skipMemoryCache(true)
            .error(R.drawable.ic_launcher_background);


    private static SimpleTarget sSimpleTarget=new SimpleTarget() {
        @Override
        public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {

        }
    };
}
