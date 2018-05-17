package cc.cy.uildemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zcy on 2018/5/17.
 */

public class PicassoUtil {
    /**
     * 优点
     * 1.可配置程度高
     * 2.自带内存磁盘二级缓存（高速缓存，缓存大小取决于设备）
     * 3.多个数据源
     * 4.线程池实现异步加载速度快
     * 5.使用非常简单
     * 6.兼容性好(版本兼容性)
     * 7.使用复杂图片转换降低内存消耗（比UIL消耗内存少）
     * 8.可指示图片加载源
     * 9.自动处理划出屏幕外的图片请求,并给对应的ImageView加载出正确的资源
     * 10.占位图及错误图片配置
     * 11.支持图片优先级加载
     * 12.Picasso提供了三种设置Tag的方式可在滑动时停止请求,onDestory()方法中进行相应处理否则会引起内存泄露
     * 13.可自定义Picasso（如下载、内存缓存策略等）
     * Picasso默认的缓存分配大小特点:
     * LRU缓存占应用程序可用内存的15%
     * 本地缓存占到硬盘空间的2%但不超过50M并且不小于5M(前提是这种情况只在4.0以上有效果,或者你能像OKHttp那样提供一个本地缓存库来支持全平台)
     * Picasso默认开启3个线程来进行本地与网络之间的访问
     * Picasso加载图片顺序, 内存–>本地–>网络
     * <p>
     * <p>
     * 缺点
     *
     * @param url
     * @param img
     */
    public static void load(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .centerCrop()//图片会被剪切
                .fit()//智能展示图片，计算最佳图片大小及质量//需要一边固定大小 否则可能不显示
                //.centerInside()//图片被完整显示，可能不被填满，被拉伸或挤压
                //.priority(Picasso.Priority.HIGH)//图片优先级
                //.tag("添加标记")//然后在onScrollStateChanged方法里进行滑动判断调用Picasso.get().resumeTag("添加标记");等方实现滑动时停止请求
                .into(img);
        //开发时可以打开彩带显示来指示图片源:network\disk\memory
        Picasso.get().setIndicatorsEnabled(true);

    }

    public static void load(File file, ImageView img) {
        Picasso.get().load(file).into(img);
        //开发时可以打开彩带显示来指示图片源:network\disk\memory
        Picasso.get().setIndicatorsEnabled(true);
    }

    public static void load(int id, ImageView img) {
        Picasso.get().load(id).into(img);
        //开发时可以打开彩带显示来指示图片源:network\disk\memory
        Picasso.get().setIndicatorsEnabled(true);
    }

    public static void load(Uri uri, ImageView img) {
        Picasso.get().load(uri).into(img);
        //开发时可以打开彩带显示来指示图片源:network\disk\memory
        Picasso.get().setIndicatorsEnabled(true);
    }

    public static void load(String url, ImageView img, Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        Picasso picasso = builder
                .downloader(new Downloader() {
                    @NonNull
                    @Override
                    public Response load(@NonNull Request request) throws IOException {
                        return null;
                    }

                    @Override
                    public void shutdown() {

                    }
                })
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .indicatorsEnabled(true)
                .loggingEnabled(true)
                .memoryCache(new LruCache(100 * 1024 * 1024))
                .build();
        picasso.load(url).fit().into(img);


    }

}
