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

}