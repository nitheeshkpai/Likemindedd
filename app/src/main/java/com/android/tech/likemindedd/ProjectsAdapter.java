package com.android.tech.likemindedd;

/**
 * Created by nitheeshkpai on 7/31/17.
 * Class that handles all DB stuff used in Save Article feature
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<ProjectItemInfo> ProjectItemsInfoList;

    public ProjectsAdapter(Context context, List<ProjectItemInfo> newsItemsInfoList) {
        this.mContext = context;
        this.ProjectItemsInfoList = newsItemsInfoList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ProjectItemInfo currentItem;
        public final TextView title;
        public final ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(mContext, ProjectActivity.class);
                    intent.putExtra("projectName", currentItem.getProjectName());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_card_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.currentItem = ProjectItemsInfoList.get(position);

        holder.title.setText(holder.currentItem.getTitle());

        //noinspection deprecation
        Picasso.with(mContext).load(holder.currentItem.getImageURL()).placeholder(mContext.getResources().getDrawable(R.mipmap.ic_launcher)).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return ProjectItemsInfoList.size();
    }
}
