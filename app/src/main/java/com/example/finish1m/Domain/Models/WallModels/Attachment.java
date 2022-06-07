
package com.example.finish1m.Domain.Models.WallModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attachment {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("photo")
    @Expose
    public Photo photo;
    @SerializedName("video")
    @Expose
    public Video video;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Attachment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("photo");
        sb.append('=');
        sb.append(((this.photo == null)?"<null>":this.photo));
        sb.append(',');
        sb.append("video");
        sb.append('=');
        sb.append(((this.video == null)?"<null>":this.video));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
