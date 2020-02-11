package com.transvision.demo;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    DatabaseHelper helper;

    private MyHelper mh;
    private SQLiteDatabase sdb;
    private String databasepath = "";
    private String databasefolder = "database";
    private String database_name = "User.db";

    public DatabaseHelper(Context context) {
        try {
            databasepath = filestorepath(databasefolder) + File.separator + database_name;
            mh = new MyHelper(context, databasepath, null, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        sdb = mh.getWritableDatabase();
        sdb = mh.getReadableDatabase();
    }

    public void close() {
        sdb.close();
    }

    public class MyHelper extends SQLiteOpenHelper {

        public MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table Users(FirstName Text,LastName Text,Gender Text,Language Text,Skill Text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            if (i!= i1) {
                // Simplest implementation is to drop all old tables and recreate them
                sdb.execSQL("DROP TABLE IF EXISTS  Users");

                onCreate(sdb);
            }

        }
    }

    public void insert_user(ContentValues cv) {

        sdb.insert("Users", null, cv);
    }


    public void updateUser(String firstName, String lastName, String gender, String language, String Skill) {
        sdb = mh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Firstname", firstName);
        values.put("LastName", lastName);
        values.put("Gender", gender);
        values.put("Language", language);
        values.put("Skill", Skill);
        sdb = mh.getWritableDatabase();
        sdb.update("Users", values, "FirstName" + "=?", new String[]{firstName});
        sdb.close();
    }


    public void deleteUsers(String firstname) {
        sdb = mh.getWritableDatabase();
        sdb.delete("Users", "FirstName" + " = ?", new String[]{firstname});
        sdb.close();
    }


    //select all the data of database
    public List<User> getAllUser() {

    List<User> usersdetails = new ArrayList<>();

    String Users_Detail = "SELECT * FROM Users";

    sdb = mh.getReadableDatabase ();
    Cursor cursor = sdb.rawQuery(Users_Detail, null);

    try {
        if (cursor.moveToFirst()) {
            do {
                User userData = new User();
                userData.FirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
                userData.LastName = cursor.getString(cursor.getColumnIndex("LastName"));
                userData.Gender = cursor.getString(cursor.getColumnIndex("Gender"));
                userData.Skill = cursor.getString(cursor.getColumnIndex("Language"));
                userData.Language = cursor.getString(cursor.getColumnIndex("Skill"));

                usersdetails.add(userData);

            } while (cursor.moveToNext());
        }
    } catch (Exception e) {
        Log.d("this", "Error while trying to get posts from database");
    } finally {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    return usersdetails;

}

    public String filestorepath(String value) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + value);
        return folder.toString();
    }


    }


