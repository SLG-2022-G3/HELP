package com.slg.G3.sos.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("UserInfos")

public class UserInfo extends ParseObject {

    public static final String KEY_SOS = "SOSMessage";
    public static final String KEY_USER = "user";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_PROFILE_IMAGE = "profilePicture";
    public static final String KEY_BLOODTYPE = "bloodType";

    public String getSOS() {return getString(KEY_SOS);}
    public void setSOS(String SOSMessage) {put(KEY_SOS, SOSMessage);}

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getTelephone() {return getString(KEY_TELEPHONE);}
    public void setTelephone(String telephone) {put(KEY_TELEPHONE, telephone);}

    public ParseFile getImage() {return getParseFile(KEY_PROFILE_IMAGE); }
    public void setImage (ParseFile parseFile) {put(KEY_PROFILE_IMAGE, parseFile);}

    public String getBloodType() {return getString(KEY_BLOODTYPE);}
    public void setBloodType(String bloodType) {put(KEY_BLOODTYPE, bloodType);}


}
