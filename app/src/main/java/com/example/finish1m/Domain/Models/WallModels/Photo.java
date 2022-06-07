
package com.example.finish1m.Domain.Models.WallModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Photo {

    @SerializedName("album_id")
    @Expose
    public int albumId;
    @SerializedName("date")
    @Expose
    public long date;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("owner_id")
    @Expose
    public int ownerId;
    @SerializedName("access_key")
    @Expose
    public String accessKey;
    @SerializedName("post_id")
    @Expose
    public int postId;
    @SerializedName("sizes")
    @Expose
    public ArrayList<Size> sizes = null;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("has_tags")
    @Expose
    public boolean hasTags;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Photo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("albumId");
        sb.append('=');
        sb.append(this.albumId);
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(this.date);
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("ownerId");
        sb.append('=');
        sb.append(this.ownerId);
        sb.append(',');
        sb.append("accessKey");
        sb.append('=');
        sb.append(((this.accessKey == null)?"<null>":this.accessKey));
        sb.append(',');
        sb.append("postId");
        sb.append('=');
        sb.append(this.postId);
        sb.append(',');
        sb.append("sizes");
        sb.append('=');
        sb.append(((this.sizes == null)?"<null>":this.sizes));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("hasTags");
        sb.append('=');
        sb.append(this.hasTags);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
