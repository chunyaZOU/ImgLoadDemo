package cc.cy.uildemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

/**
 * Created by zcy on 2018/5/18.
 */

public class FrescoUtil {

    private static GenericDraweeHierarchy sDraweeHierarchy;
    private static MemoryTrimmableRegistry sMemoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();

    /**
     * 1.使用fresco需要先初始化
     * 2.底层使用c++代码 包体积更大
     * 3.SimpleDraweeView does not support wrap_content for layout_width or layout_height attributes
     * 4.xml中配置SimpleDraweeView属性，也可代码中配置 必须使用SimpleDraweeView
     * 5.自动回收内存
     * 6.三级缓存（解码与未解码与磁盘）
     * 7.支持场景多（动图、模糊、渐进、进度等）
     * 8.支持GIF、webp（需要额外添加依赖）
     * 9.允许解码时调整图片大小，默认只支持JPEG图，所以要设置该属性来支持png、jpg、webp。方法setDownsampleEnabled
     * 10.setHierarchy不要在同一个view上调用多次,即使view被回收
     * 11.可以通过Hierarchy设置图片加载行为
     *
     * @param context
     */
    public static void init(Context context) {

        //内存紧张应对
        sMemoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();

                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio) {
                    //清空内存缓存
                    ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
                }
            }
        });

        ImagePipelineConfig.Builder builder
                = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)//磁盘缓存
                        .setMaxCacheSize(100 * 1024 * 1024)
                        .setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())
                        .setBaseDirectoryName("frsco")
                        .build())
                .setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {//解码内存缓存
                    @Override
                    public MemoryCacheParams get() {
                        int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
                        int MAX_MEMORY_SIZE = MAX_HEAP_SIZE / 5;

                        return new MemoryCacheParams(
                                // 可用最大内存数，以字节为单位
                                MAX_MEMORY_SIZE,
                                // 内存中允许的最多图片数量
                                100,
                                // 内存中准备清理但是尚未删除的总图片所可用的最大内存数，以字节为单位
                                MAX_MEMORY_SIZE,
                                // 内存中准备清除的图片最大数量
                                100,
                                // 内存中单图片的最大大小
                                Integer.MAX_VALUE);
                    }
                }).setEncodedMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {//未解码内存缓存
                    @Override
                    public MemoryCacheParams get() {
                        int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
                        int MAX_MEMORY_SIZE = MAX_HEAP_SIZE / 5;
                        //未解码内存缓存参考上边
                        return new MemoryCacheParams(
                                // 可用最大内存数，以字节为单位
                                MAX_MEMORY_SIZE,
                                // 内存中允许的最多图片数量
                                100,
                                // 内存中准备清理但是尚未删除的总图片所可用的最大内存数，以字节为单位
                                MAX_MEMORY_SIZE,
                                // 内存中准备清除的图片最大数量
                                100,
                                // 内存中单图片的最大大小
                                Integer.MAX_VALUE);
                    }
                }).setMemoryTrimmableRegistry(sMemoryTrimmableRegistry)
                .setDownsampleEnabled(true)//允许解码时调整图片大小，默认只支持JPEG图
                .setBitmapsConfig(Bitmap.Config.RGB_565);//默认8888


        Fresco.initialize(context, builder.build());
    }
}
