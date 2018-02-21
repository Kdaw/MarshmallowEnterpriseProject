package com.example.karld.marshmallowenp;

/**
 * Created by karld on 21/02/2018.
 */

public class Post {
    private static String postID;
    private static String userID;
    private static String postTitle;
    private static String details;
    private static String location; //TODO update with location details for Google Maps
    //TODO add image type variable
    private static int reviewed; // either 1 or 0 to add to firebase. Use YES or NO
    private static final int YES = 1;
    private static final int NO = 0;
    private static int available;
    //TODO add list of bidders
        private static String bidders;
    private static String bidWonBy;

    private Post(String uID, String title, String deets, String loc){
        //push for post id then add rest as child
        userID = uID;
        postTitle = title;
        details = deets;
        location = loc;
        reviewed = NO;
    }

    public static Post CreatePost(String id, String ttl, String dts, String loca) {
        return new Post(id, ttl, dts, loca);
    }
}
