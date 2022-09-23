package com.slg.G3.sos.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Contact extends ParseObject {

    public static final String KEY_NAME = "nom";
    public static final String KEY_PHONE = "telephone";
    public static final String KEY_USER = "user";
    public static final String KEY_PROFILE_PIC = "profilePhoto";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_RELATIONSHIP = "relationship";
    public static final String KEY_UPDATED_AT = "updatedAt";

    public String getName(){
        return getString(KEY_NAME);
    }
    public void setName(String nom) {
        put(KEY_NAME, nom);
    }

    public int getPhone(){
        return getNumber(KEY_PHONE);
    }
    public void setPhone(Number telephone) {
        put(KEY_PHONE, telephone);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_PROFILE_PIC);
    }
    public void setImage(ParseFile parseFile) {
        put(KEY_PROFILE_PIC, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
    public String getAddress(){
        return getString(KEY_ADDRESS);
    }
    public void setAddress(String address) {
        put(KEY_ADDRESS, address);
    }

    public String getRelationship(){
        return getString(KEY_RELATIONSHIP);
    }
    public void setRelationship(String relationship) {
        put(KEY_RELATIONSHIP, relationship);
    }

    public String getUpdatedAt(){
        return getString(KEY_UPDATED_AT);
    }
    public void setUpdatedAt(String updatedAt) {
        put(KEY_UPDATED_AT, updatedAt);
    }
}