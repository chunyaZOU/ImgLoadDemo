package cc.cy.uildemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zcy on 2018/5/17.
 */

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mUrls;
    private LayoutInflater mInflater;

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
            convertView = mInflater.inflate(R.layout.view_list_item, null);
            holder = new ViewHolder();
            holder.mImg = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //ImageLoaderUtil.getImageLoader(mContext).displayImage(mUrls.get(position), holder.mImg);
        PicassoUtil.load(mUrls.get(position), holder.mImg);
        return convertView;
    }

    static class ViewHolder {
        public ImageView mImg;
    }
}
