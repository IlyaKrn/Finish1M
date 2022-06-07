
package com.example.finish1m.Domain.Models.WallModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Size {

    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("width")
    @Expose
    public int width;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Size.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("height");
        sb.append('=');
        sb.append(this.height);
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(this.width);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
