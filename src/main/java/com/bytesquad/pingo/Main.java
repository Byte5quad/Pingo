package com.bytesquad.pingo;

import com.bytesquad.pingo.model.Message;
import com.bytesquad.pingo.model.User;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        User testUser = new User("Test name", 1);
        Message testMessage = new Message(testUser, "this is a test message.");
        
        System.out.println(testMessage);
    }
}
