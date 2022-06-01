package com.example.finish1m.Presentation;

import com.example.finish1m.Domain.Models.User;

public class PresentationConfig {

    public static final String REF_VK_MAIN = "https://vk.com/club197453413";
    public static final int SMALL_MESSAGE_SIZE = 200;
    public static User user; // текущий пользователь

    public static User getUser() {
        synchronized (PresentationConfig.class) {
            if (user == null){
                return new User("Ошибка", "Ошибка", "Ошибка", false, false, null);
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
