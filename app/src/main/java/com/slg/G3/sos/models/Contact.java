package com.slg.G3.sos.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Contact")
public class Contact extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "profilePhoto";
    public static final String KEY_USER = "user";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_RELATIONSHIP = "relationship";
    public static final String KEY_ADDRESS = "address";



    public String getName(){
        return getString(KEY_NAME);
    }
    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String getNumber(){
        return getString(KEY_PHONE);
    }
    public void setNumber(String phone) {
        put(KEY_PHONE, phone);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile photoProfile) {
        put(KEY_IMAGE, photoProfile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getRelationship(){
        return getString(KEY_RELATIONSHIP);
    }
    public void setRelationship(String relationship) {
        put(KEY_RELATIONSHIP, relationship);
    }

    public String getAddress(){
        return getString(KEY_ADDRESS);
    }
    public void setAddress(String address) {
        put(KEY_ADDRESS, address);
    }

}