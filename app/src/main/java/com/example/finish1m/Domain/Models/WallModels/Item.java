
package com.example.finish1m.Domain.Models.WallModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Item {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("from_id")
    @Expose
    public int fromId;
    @SerializedName("owner_id")
    @Expose
    public int ownerId;
    @SerializedName("date")
    @Expose
    public long date;
    @SerializedName("marked_as_ads")
    @Expose
    public int markedAsAds;
    @SerializedName("is_favorite")
    @Expose
    public boolean isFavorite;
    @SerializedName("post_type")
    @Expose
    public String postType;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("copy_history")
    @Expose
    public ArrayList<CopyHistory> copyHistory = null;
    @SerializedName("attachments")
    @Expose
    public ArrayList<Attachment> attachments = null;
    @SerializedName("comments")
    @Expose
    public Comments comments;
    @SerializedName("likes")
    @Expose
    public Likes likes;
    @SerializedName("reposts")
    @Expose
    public Reposts reposts;
    @SerializedName("views")
    @Expose
    public Views views;
    @SerializedName("donut")
    @Expose
    public Donut donut;
    @SerializedName("short_text_rate")
    @Expose
    public double shortTextRate;
    @SerializedName("hash")
    @Expose
    public String hash;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Item.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("fromId");
        sb.append('=');
        sb.append(this.fromId);
        sb.append(',');
        sb.append("ownerId");
        sb.append('=');
        sb.append(this.ownerId);
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(this.date);
        sb.append(',');
        sb.append("markedAsAds");
        sb.append('=');
        sb.append(this.markedAsAds);
        sb.append(',');
        sb.append("isFavorite");
        sb.append('=');
        sb.append(this.isFavorite);
        sb.append(',');
        sb.append("postType");
        sb.append('=');
        sb.append(((this.postType == null)?"<null>":this.postType));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("copyHistory");
        sb.append('=');
        sb.append(((this.copyHistory == null)?"<null>":this.copyHistory));
        sb.append("attachments");
        sb.append('=');
        sb.append(((this.attachments == null)?"<null>":this.attachments));
        sb.append(',');
        sb.append("comments");
        sb.append('=');
        sb.append(((this.comments == null)?"<null>":this.comments));
        sb.append(',');
        sb.append("likes");
        sb.append('=');
        sb.append(((this.likes == null)?"<null>":this.likes));
        sb.append(',');
        sb.append("reposts");
        sb.append('=');
        sb.append(((this.reposts == null)?"<null>":this.reposts));
        sb.append(',');
        sb.append("views");
        sb.append('=');
        sb.append(((this.views == null)?"<null>":this.views));
        sb.append(',');
        sb.append("donut");
        sb.append('=');
        sb.append(((this.donut == null)?"<null>":this.donut));
        sb.append(',');
        sb.append("shortTextRate");
        sb.append('=');
        sb.append(this.shortTextRate);
        sb.append(',');
        sb.append("hash");
        sb.append('=');
        sb.append(((this.hash == null)?"<null>":this.hash));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
