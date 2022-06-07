
package com.example.finish1m.Domain.Models.WallModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostSource {

    @SerializedName("platform")
    @Expose
    public String platform;
    @SerializedName("type")
    @Expose
    public String type;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PostSource.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("platform");
        sb.append('=');
        sb.append(((this.platform == null)?"<null>":this.platform));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
