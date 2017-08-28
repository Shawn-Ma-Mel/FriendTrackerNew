package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class FriendTracker {
    //产生随机String
    public static String getRandomString(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return s;
    }
    private static ArrayList<Friend> friendArrayList = new ArrayList<>();

    public static ArrayList<Friend> getFriendArrayList() {
        return friendArrayList;
    }
    public static void addFriend (String id, String name, String email){
        friendArrayList.add(new Friend(id,name,email));
    }

    public static String getName(int index){
        return friendArrayList.get(index).getName();
    }
    public static String getEmail(int index){
        return friendArrayList.get(index).getEmail();
    }
    public static int listCount(){
        return friendArrayList.size();
    }

}
