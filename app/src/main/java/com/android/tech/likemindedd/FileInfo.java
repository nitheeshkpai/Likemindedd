package com.android.tech.likemindedd;

import android.os.Parcel;
import android.os.Parcelable;

public class FileInfo  implements Parcelable {

    private String provider;
    private String type;
    private String url;
    private String thumbnail;

    public FileInfo() {

    }

    protected FileInfo(Parcel in) {
        provider = in.readString();
        type = in.readString();
        url = in.readString();
        thumbnail = in.readString();
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

    public String getProvider() {
        return provider;
    }

    public String getURL() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isGIF() {
        return url.contains(".gif")? true : false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(provider);
        dest.writeString(thumbnail);
    }

}
