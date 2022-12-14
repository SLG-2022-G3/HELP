package com.slg.G3.sos.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("User")

public class User extends ParseUser {

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_SOS = "sosMessage";
    public static final String KEY_INFO_PERSO = "persoInfo";
    public static final String KEY_TELEPHONE = "phoneNumber";
    public static final String KEY_PROFILE_IMAGE = "profilePicture";


    public String getEmail(){
        return getString(KEY_EMAIL);
    }
    public void setEmail(String email) {
        put(KEY_EMAIL, email);
    }

    public String getPassword(){
        return getString(KEY_PASSWORD);
    }
    public void setPassword(String password) {
        put(KEY_PASSWORD, password);
    }

    public String getUsername(){
        return getString(KEY_USERNAME);
    }
    public void setUsername(String username) {
        put(KEY_USERNAME, username);
    }

    public String getSOS(){
        return getString(KEY_SOS);
    }
    public void setSOS(String sosMessage) {
        put(KEY_SOS, sosMessage);
    }

    public String getInfo(){
        return getString(KEY_INFO_PERSO);
    }
    public void setInfo(String persoInfo) {
        put(KEY_INFO_PERSO, persoInfo);
    }

    public String getTelephone(){
        return getString(KEY_TELEPHONE);
    }
    public void setTelephone(String phoneNumber) {
        put(KEY_TELEPHONE, phoneNumber);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }
    public void setImage(ParseFile parseFile) {
        put(KEY_PROFILE_IMAGE, parseFile);
    }


}