package com.android.tech.likemindedd;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    List<FileInfo> fileInfoList = new ArrayList<>();
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, List<FileInfo> fileInfoList) {
        this.context = context;
        this.fileInfoList = fileInfoList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final FileInfo fileInfo = fileInfoList.get(position);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        if(fileInfo.getType().equals("image")){
            if(fileInfo.isGIF()){
                ImageView image = (ImageView) itemView.findViewById(R.id.imageView);
                Glide.with(context).load(fileInfo.getURL()).asGif().into(image);
                image.setVisibility(View.VISIBLE);
            } else {
                ImageView image = (ImageView) itemView.findViewById(R.id.imageView);
                Glide.with(context).load(fileInfo.getThumbnail()).into(image);
                image.setVisibility(View.VISIBLE);
            }
        } else if(fileInfo.getType().equals("video")) {
            ImageView image = (ImageView) itemView.findViewById(R.id.youtubeview);
            itemView.setBackgroundColor(context.getResources().getColor(R.color.black));
            image.setVisibility(View.VISIBLE);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, YoutubeActivity.class);
                    i.putExtra("link",fileInfo.getURL());
                    context.startActivity(i);
                }
            });

        }
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((View) object);

    }

}