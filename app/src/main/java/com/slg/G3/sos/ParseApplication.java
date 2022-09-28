package com.slg.G3.sos;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.slg.G3.sos.models.Contact;
import com.slg.G3.sos.models.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Contact.class);
        ParseObject.registerSubclass(User.class);


        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // any network interceptors must be added with the Configuration Builder given this syntax
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // Set applicationId and server based on the values in the Back4App settings.
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3YBkP2I6OuAieS4KJfkjdz2cxR8gbZRFwJ1J4R7O") // ⚠️ TYPE IN A VALID APPLICATION ID HERE
                .clientKey("QdCHqYbsuk70zVHy9xIzjFVjZ9DrjNiZbRQLMRJT") // ⚠️ TYPE IN A VALID CLIENT KEY HERE
                .clientBuilder(builder)
                .server("https://parseapi.back4app.com").build());  // ⚠️ TYPE IN A VALID SERVER URL HERE


    }
}
