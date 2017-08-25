package com.android.tech.likemindedd;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nitheeshkpai on 7/31/17.
 * Class that handles all DB stuff used in Save Article feature
 */

public class ProjectInfo  implements Parcelable {

    private int id;
    private String title;
    private String usp;
    private String description;

    @SerializedName("pinned_image_display")
    private String imageUrl;

    @SerializedName("cover_path")
    private String coverPath;

    @SerializedName("released_date")
    private String releaseDate;

    @SerializedName("press_kit_link")
    private String presskitLink;

    @SerializedName("demo_link")
    private String demoLink;

    @SerializedName("demo_link_attribute")
    private String demoLinkAttribute;

    @SerializedName("engine")
    private String engine;

    @SerializedName("developement_stage")
    private String developmentStage;

    @SerializedName("team_title")
    private String teamTitle;


    public ProjectInfo() {

    }

    protected ProjectInfo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imageUrl = in.readString();
        teamTitle = in.readString();
        developmentStage = in.readString();
        engine = in.readString();
        demoLinkAttribute = in.readString();
        demoLink = in.readString();
        presskitLink = in.readString();
        releaseDate = in.readString();
        coverPath = in.readString();
        description = in.readString();
        usp = in.readString();

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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public String getTeamTitle() {
        return teamTitle;
    }

    public String getDevelopmentStage() {
        return developmentStage;
    }

    public String getEngine() {
        return engine;
    }

    public String getDemoLinkAttribute() {
        return demoLinkAttribute;
    }

    public String getDemoLink() {
        return demoLink;
    }

    public String getPresskitLink() {
        return presskitLink;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public String getDescription() {
        return description;
    }

    public String getUsp() {
        return usp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(teamTitle);
        dest.writeString(developmentStage);
        dest.writeString(engine);
        dest.writeString(demoLinkAttribute);
        dest.writeString(demoLink);
        dest.writeString(presskitLink);
        dest.writeString(releaseDate);
        dest.writeString(coverPath);
        dest.writeString(description);
        dest.writeString(usp);

    }

}
