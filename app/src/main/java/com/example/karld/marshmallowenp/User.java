package com.example.karld.marshmallowenp;

/**
 * Created by karld on 21/02/2018.
 */

public class User {
    private static String uID;
    private static double rating;
    private static String Email;
    //TODO add profile picture variable when needed

    private User( double rate, String uName) {
        //uID = id;
        rating = rate;
        Email = uName;
    }

    public static User CreateUser( double rate, String uName) {
        return new User( 0.0, uName);
    }

    public static String getuID() {
        return uID;
    }

    public static double getRating() {
        return rating;
    }

    public static String getName() {
        return Email;
    }

    public static void setuID(String uID) {
        User.uID = uID;
    }

    public static void setRating(double rating) {
        User.rating = rating;
    }

    public static void setName(String name) {
        User.Email = name;
    }
}
