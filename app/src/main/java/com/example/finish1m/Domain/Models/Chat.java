package com.example.finish1m.Domain.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {

    private String id;
    private HashMap<String, Message> messages;

    public Chat(String id, HashMap<String, Message> messages, ArrayList<String> members) {
        this.id = id;
        this.messages = messages;
    }

    public Chat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, Message> messages) {
        this.messages = messages;
    }

}
