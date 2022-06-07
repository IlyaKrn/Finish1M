package com.example.finish1m.Presentation;

import com.example.finish1m.Domain.Models.User;

public class PresentationConfig {

    public static final String REF_VK_MAIN = "https://vk.com/club197453413";
    public static final String REF_VK_WALL_POST_EXAMPLE = "https://vk.com/club197453413?w=wall-197453413_433%2Fall";
    public static final int SMALL_MESSAGE_SIZE = 200;
    public static User user; // текущий пользователь

    public static User getUser() throws Exception {
        synchronized (PresentationConfig.class) {
            if (user == null){
                throw new NullPointerException("user is null (thread synchronize error in PresentationConfig.java)");
            }
            return user;
        }
    }
    public static void setUser(User u) {
        synchronized (PresentationConfig.class) {
            user = u;
        }
    }
}
