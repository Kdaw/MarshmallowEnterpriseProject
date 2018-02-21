package com.example.karld.marshmallowenp;

/**
 * Created by karld on 21/02/2018.
 */

public class Review {
    private static String reviewID;
    private static String userReviewed;
    private static double score;
    private static String description;
    private static String reviewer;
    private static String postID;

    private Review(String uReviewed, double rating, String desc, String reviewedBy, String pID) {
        userReviewed = uReviewed;
        score = rating;
        description = desc;
        reviewer = reviewedBy;
        postID = pID;
    }

    public static Review CreateReview(String uReviewed, double rating, String desc, String reviewedBy, String pID) {
        return new Review(uReviewed, rating, desc, reviewedBy, pID);
    }
}
