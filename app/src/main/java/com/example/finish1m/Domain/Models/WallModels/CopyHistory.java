
package com.example.finish1m.Domain.Models.WallModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class CopyHistory {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("owner_id")
    @Expose
    public int ownerId;
    @SerializedName("from_id")
    @Expose
    public int fromId;
    @SerializedName("date")
    @Expose
    public int date;
    @SerializedName("post_type")
    @Expose
    public String postType;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("attachments")
    @Expose
    public ArrayList<Attachment> attachments = null;
    @SerializedName("post_source")
    @Expose
    public PostSource postSource;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CopyHistory.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("ownerId");
        sb.append('=');
        sb.append(this.ownerId);
        sb.append(',');
        sb.append("fromId");
        sb.append('=');
        sb.append(this.fromId);
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(this.date);
        sb.append(',');
        sb.append("postType");
        sb.append('=');
        sb.append(((this.postType == null)?"<null>":this.postType));
        sb.append(',');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("attachments");
        sb.append('=');
        sb.append(((this.attachments == null)?"<null>":this.attachments));
        sb.append(',');
        sb.append("postSource");
        sb.append('=');
        sb.append(((this.postSource == null)?"<null>":this.postSource));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
