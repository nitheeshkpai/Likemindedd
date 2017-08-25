package com.android.tech.likemindedd;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProjectItemInfo  implements Parcelable {

    private String title;

    private String projectName;

    @SerializedName("pinned_image_display")
    private String imageUrl;

    public ProjectItemInfo() {

    }

    public ProjectItemInfo(String title, String url, String projectName) {
        this.title = title;
        this.imageUrl = url;
        this.projectName = projectName;
    }

    protected ProjectItemInfo(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        projectName = in.readString();
    }

    public static final Parcelable.Creator<ProjectItemInfo> CREATOR = new Parcelable.Creator<ProjectItemInfo>() {
        @Override
        public ProjectItemInfo createFromParcel(Parcel in) {
            return new ProjectItemInfo(in);
        }

        @Override
        public ProjectItemInfo[] newArray(int size) {
            return new ProjectItemInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(projectName);
    }

}
