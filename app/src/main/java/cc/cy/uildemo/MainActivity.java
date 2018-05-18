package cc.cy.uildemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import cc.cy.uildemo.utils.ImageLoaderUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView mLv;
    private ListAdapter mListAdapter;
    private List<String> mUrls = new ArrayList<>();
    String imgUrl = "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=49dab67a3687e9505d17f56c2039531b/95eef01f3a292df51f2415acb0315c6035a873a0.jpg";
    String gifUrl = "http://storage.slide.news.sina.com.cn/slidenews/77_ori/2018_20/74766_822907_715346.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = findViewById(R.id.lv);
        //使用UIL时 滑动暂停加载
        //mLv.setOnScrollListener(new PauseOnScrollListener(ImageLoaderUtil.getImageLoader(this), true, true));
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
            if (i % 2 == 0)
                mUrls.add(imgUrl);
            else mUrls.add(gifUrl);
        }
        mListAdapter = new ListAdapter(this, mUrls);
        mListAdapter.setUseFresco(true);
        mLv.setAdapter(mListAdapter);
    }
}
