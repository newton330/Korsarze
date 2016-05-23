package com.example.newto.korsarze;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.newto.korsarze.R;


public class ImageAdapterOpponent extends BaseAdapter {
    public Context mContext;

    public ImageAdapterOpponent(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 100;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100,100));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(3,3,3,3);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[4]);
        return imageView;
    }

    public Integer[] mThumbIds = new Integer[]{
            R.drawable.water, R.drawable.water, R.drawable.ship, R.drawable.hit, R.drawable.unknown
    };
}