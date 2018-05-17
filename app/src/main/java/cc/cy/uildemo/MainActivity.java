package cc.cy.uildemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView mLv;
    private ListAdapter mListAdapter;
    private List<String> mUrls = new ArrayList<>();
    String imgUrl = "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=49dab67a3687e9505d17f56c2039531b/95eef01f3a292df51f2415acb0315c6035a873a0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = findViewById(R.id.lv);

        mLv.setOnScrollListener(new PauseOnScrollListener(ImageLoaderUtil.getImageLoader(this), true, true));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            } else loadImg();
        } else loadImg();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImg();
        } else {
            Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImg() {
        for (int i = 0; i < 50; i++) {
            mUrls.add(imgUrl);
        }
        mListAdapter = new ListAdapter(this, mUrls);
        mLv.setAdapter(mListAdapter);
    }
}
