package com.example.karld.marshmallowenp;

/**
 * Created by karld on 21/02/2018.
 */

public class User {
    private static String uID;
    private static double rating;
    private static String name;
    //TODO add profile picture variable when needed

    private User(String id, double rate, String uName) {
        uID = id;
        rating = rate;
        name = uName;
    }

    public User CreateUser(String id, double rate, String uName) {
        return new User(id, 0.0, uName);
    }
}
