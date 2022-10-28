package com.slg.G3.sos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MyContacts2";

    // Country table name
    private static final String TABLE_NAME = "SOScontact";

    // Country Table Columns names
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String PHONENO = "phoneNo";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create the table for the first time
        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + PHONENO + " TEXT)";

        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //method to add the contact
    public void addcontact(String name, String phoneNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(NAME, name);
        c.put(PHONENO, phoneNo);
        db.insert(TABLE_NAME, null, c);
        db.close();
    }

    //method to retrieve all the contacts in List
    public List<ContactModel> getAllContacts() {
        List<ContactModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {

                list.add(new ContactModel(c.getInt(0), c.getString(1), c.getString(2)));

            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    //get the count of data, this will allow user to not add more that five contacts in database
    public int count() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    // Deleting single country
    public void deleteContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{name});
        db.close();
    }


}
