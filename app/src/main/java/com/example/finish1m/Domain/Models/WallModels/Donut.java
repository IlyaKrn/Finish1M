
package com.example.finish1m.Domain.Models.WallModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donut {

    @SerializedName("is_donut")
    @Expose
    public boolean isDonut;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Donut.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("isDonut");
        sb.append('=');
        sb.append(this.isDonut);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
