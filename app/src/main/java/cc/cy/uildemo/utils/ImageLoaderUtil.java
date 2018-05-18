package cc.cy.uildemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * Created by zcy on 2018/5/17.
 */

public class ImageLoaderUtil {
    static ImageLoader sImageLoader;

    /**
     * 优点
     * 1.可配置程度高
     * 2.多级缓存(默认实现多种缓存算法)
     * 3.多个数据源
     * 4.线程池实现异步加载速度快
     * 5.可监听下载进度
     * 6.使用简单
     * 7.兼容性好(版本兼容性)
     * 8.可在view滚动中暂停图片加载PauseOnScrollListener
     * <p>
     * <p>
     * 缺点
     * 1.已经不再更新维护
     *
     * @param context
     * @return
     */
    public static ImageLoader getImageLoader(Context context) {
        if (sImageLoader == null) {
            sImageLoader = ImageLoader.getInstance();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            //ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
            try {
                ImageLoaderConfiguration loaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                        .memoryCacheExtraOptions(480, 800)
                        .diskCacheExtraOptions(480, 800, bitmapProcessor)
                        .taskExecutor(Executors.newCachedThreadPool())
                        .taskExecutorForCachedImages(Executors.newCachedThreadPool())
                        .threadPoolSize(5)
                        .threadPriority(Thread.NORM_PRIORITY - 1)
                        .tasksProcessingOrder(QueueProcessingType.FIFO)
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new LruMemoryCache(50 * 1024 * 1024))
                        .memoryCacheSize(50 * 1024 * 1024)
                        .memoryCacheSizePercentage(15)
                        .diskCache(new LruDiskCache(new File(path), generator, 100))
                        .diskCacheSize(100 * 1024 * 1024)
                        .diskCacheFileCount(100)
                        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                        .imageDownloader(new BaseImageDownloader(context))
                        .imageDecoder(new BaseImageDecoder(true))
                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        .writeDebugLogs()
                        .build();
                sImageLoader.setDefaultLoadingListener(listener);
                sImageLoader.init(loaderConfiguration);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sImageLoader;
    }

    private static final FileNameGenerator generator = new FileNameGenerator() {
        @Override
        public String generate(String s) {
            return String.valueOf(s.hashCode());
        }
    };

    private static final BitmapProcessor bitmapProcessor = new BitmapProcessor() {
        @Override
        public Bitmap process(Bitmap bitmap) {
            return bitmap;
        }
    };
    private static final SimpleImageLoadingListener listener = new SimpleImageLoadingListener() {
        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            super.onLoadingCancelled(imageUri, view);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            super.onLoadingFailed(imageUri, view, failReason);
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
        }
    };
}
