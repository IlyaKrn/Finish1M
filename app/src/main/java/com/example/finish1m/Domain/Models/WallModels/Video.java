
package com.example.finish1m.Domain.Models.WallModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Video {

    @SerializedName("access_key")
    @Expose
    public String accessKey;
    @SerializedName("can_comment")
    @Expose
    public int canComment;
    @SerializedName("can_like")
    @Expose
    public int canLike;
    @SerializedName("can_repost")
    @Expose
    public int canRepost;
    @SerializedName("can_subscribe")
    @Expose
    public int canSubscribe;
    @SerializedName("can_add_to_faves")
    @Expose
    public int canAddToFaves;
    @SerializedName("can_add")
    @Expose
    public int canAdd;
    @SerializedName("comments")
    @Expose
    public int comments;
    @SerializedName("date")
    @Expose
    public int date;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("duration")
    @Expose
    public int duration;
    @SerializedName("image")
    @Expose
    public ArrayList<Image> image = null;
    @SerializedName("first_frame")
    @Expose
    public ArrayList<FirstFrame> firstFrame = null;
    @SerializedName("width")
    @Expose
    public int width;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("owner_id")
    @Expose
    public int ownerId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("is_favorite")
    @Expose
    public boolean isFavorite;
    @SerializedName("track_code")
    @Expose
    public String trackCode;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("views")
    @Expose
    public int views;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Video.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("accessKey");
        sb.append('=');
        sb.append(((this.accessKey == null)?"<null>":this.accessKey));
        sb.append(',');
        sb.append("canComment");
        sb.append('=');
        sb.append(this.canComment);
        sb.append(',');
        sb.append("canLike");
        sb.append('=');
        sb.append(this.canLike);
        sb.append(',');
        sb.append("canRepost");
        sb.append('=');
        sb.append(this.canRepost);
        sb.append(',');
        sb.append("canSubscribe");
        sb.append('=');
        sb.append(this.canSubscribe);
        sb.append(',');
        sb.append("canAddToFaves");
        sb.append('=');
        sb.append(this.canAddToFaves);
        sb.append(',');
        sb.append("canAdd");
        sb.append('=');
        sb.append(this.canAdd);
        sb.append(',');
        sb.append("comments");
        sb.append('=');
        sb.append(this.comments);
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(this.date);
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("duration");
        sb.append('=');
        sb.append(this.duration);
        sb.append(',');
        sb.append("image");
        sb.append('=');
        sb.append(((this.image == null)?"<null>":this.image));
        sb.append(',');
        sb.append("firstFrame");
        sb.append('=');
        sb.append(((this.firstFrame == null)?"<null>":this.firstFrame));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(this.width);
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(this.height);
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("ownerId");
        sb.append('=');
        sb.append(this.ownerId);
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("isFavorite");
        sb.append('=');
        sb.append(this.isFavorite);
        sb.append(',');
        sb.append("trackCode");
        sb.append('=');
        sb.append(((this.trackCode == null)?"<null>":this.trackCode));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("views");
        sb.append('=');
        sb.append(this.views);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
