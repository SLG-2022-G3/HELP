package com.slg.G3.sos.models;

import com.parse.ParseFile;
import com.parse.ParseUser;

public class User extends ParseUser {

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_SOS = "sosMessage";
    public static final String KEY_TELEPHONE = "telephone";
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

    public String getSos(){
        return getString(KEY_SOS);
    }
    public void setSos(String sos) {
        put(KEY_SOS, sos);
    }

    public String getTelephone(){
        return getString(KEY_TELEPHONE);
    }
    public void setTelephone(String telephone) {
        put(KEY_TELEPHONE, telephone);
    }

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }
    public void setProfileImage(ParseFile profileImage) {
        put(KEY_PROFILE_IMAGE, profileImage);
    }

}
