
package com.example.finish1m.Domain.Models.WallModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Likes {

    @SerializedName("can_like")
    @Expose
    public int canLike;
    @SerializedName("count")
    @Expose
    public int count;
    @SerializedName("user_likes")
    @Expose
    public int userLikes;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Likes.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("canLike");
        sb.append('=');
        sb.append(this.canLike);
        sb.append(',');
        sb.append("count");
        sb.append('=');
        sb.append(this.count);
        sb.append(',');
        sb.append("userLikes");
        sb.append('=');
        sb.append(this.userLikes);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
