package cc.cy.uildemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cc.cy.uildemo.utils.FrescoUtil;
import cc.cy.uildemo.utils.glide.GlideUtil;

/**
 * Created by zcy on 2018/5/17.
 */

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mUrls;
    private LayoutInflater mInflater;
    private boolean mUseFresco;

    public ListAdapter(Context context, List<String> urls) {
        mContext = context;
        mUrls = urls;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return mUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(isUseFresco() ? R.layout.view_fresco_list_item : R.layout.view_list_item, null);
            holder = new ViewHolder();
            holder.mImg = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //ImageLoaderUtil.getImageLoader(mContext).displayImage(mUrls.get(position), holder.mImg);
        //PicassoUtil.load(mUrls.get(position), holder.mImg);
        if (isUseFresco()) {
            GenericDraweeHierarchy draweeHierarchy = ((SimpleDraweeView) holder.mImg).getHierarchy();
            draweeHierarchy.setFadeDuration(1000);
            draweeHierarchy.setFailureImage(R.drawable.ic_launcher_background);
            draweeHierarchy.setPlaceholderImage(R.drawable.ic_launcher_background);
            draweeHierarchy.setRoundingParams(RoundingParams.fromCornersRadius(30f));
            ((SimpleDraweeView) holder.mImg).setHierarchy(draweeHierarchy);
            holder.mImg.setImageURI(Uri.parse(mUrls.get(position)));

        } else
            GlideUtil.load(mContext, mUrls.get(position), holder.mImg);

        return convertView;
    }

    public boolean isUseFresco() {
        return mUseFresco;
    }

    public void setUseFresco(boolean useFresco) {
        mUseFresco = useFresco;
    }

    static class ViewHolder {
        public ImageView mImg;
    }
}
