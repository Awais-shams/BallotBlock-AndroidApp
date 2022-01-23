package com.example.ballotblock.navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class DBTools extends SQLiteOpenHelper {
    View view;

    public DBTools(Context context) {
        super(context, "CandidatesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SqlCreateTable = "CREATE TABLE CANDIDATES ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,"+
                "electionType TEXT,"+
                "designation TEXT,"+
                "votingEndTime TEXT,"+
                "status TEXT);";
        try {
            db.execSQL(SqlCreateTable);
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNewCandidate(HashMap<String, String> contact) {
//        For Writing into DB
//        here db has rights for writing to DB
        SQLiteDatabase db = getWritableDatabase();
//        Arranges data in DB type format
        ContentValues contentValues = new ContentValues();
//        Insert Column/Field Names here
//        DB understands contentValues format and not hashMap format
        contentValues.put("_id", contact.get("_id"));
        contentValues.put("name", contact.get("firstName"));
        contentValues.put("electionType", contact.get("lastName"));
        contentValues.put("designation", contact.get("phoneNumber"));
        contentValues.put("votingEndTime", contact.get("emailAddress"));
        contentValues.put("status", contact.get("homeAddress"));

        long i = db.insert("CANDIDATES", null, contentValues);
        if (i != 1) {
//            Make Global View object first
//            Snackbar.make(view, "Data Inserted...", Snackbar.LENGTH_LONG).show();
            Log.d("TAG", "Data Inserted...");
        }
        else {
//            Snackbar.make(view, "Data Insertion Failed...", Snackbar.LENGTH_LONG).show();
            Log.d("TAG", "Data Insertion Failed...");
        }

    }

    //    Function to populate list from data in db, return type is ArrayList
    public ArrayList<HashMap<String, String>> getAllContacts() {
//        create object to read data from db
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM CONTACTS ORDER BY firstName DESC;";
//        Cursor will execute this query
        Cursor cursor = db.rawQuery(query, null);
//        moveToFirst means DB is not empty
        if (cursor.moveToFirst()) {
            do {
//                Declare HashMap in loop to avoid duplication
                HashMap<String, String> contact = new HashMap<String, String>();
                contact.put("_id", cursor.getString(0));
                contact.put("firstName", cursor.getString(1));
                contact.put("lastName", cursor.getString(2));
                contact.put("phoneNumber", cursor.getString(3));
                contact.put("emailAddress", cursor.getString(4));
                contact.put("homeAddress", cursor.getString(5));
//                now add hashmap(contact) to ArrayList(contactList)
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contactList;
    }

    //    create function to get 1 record for editing
    public HashMap<String, String> getSingleRecord(String id) {
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, String> singleRecord = new HashMap<String, String>();
        String query = "SELECT * FROM CONTACTS WHERE _id = '" + id + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            singleRecord.put("_id", cursor.getString(0));
            singleRecord.put("firstName", cursor.getString(1));
            singleRecord.put("lastName", cursor.getString(2));
            singleRecord.put("phoneNumber", cursor.getString(3));
            singleRecord.put("emailAddress", cursor.getString(4));
            singleRecord.put("homeAddress", cursor.getString(5));
        }
        return singleRecord;
    }

    public void DeleteRecord (String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("CONTACTS","_id = '" + id + "'" ,null);
        db.close();
    }

    public void UpdateRecord (String id, ContentValues contentValues){
        SQLiteDatabase db = getWritableDatabase();
        db.update("CONTACTS", contentValues,"_id = '" + id + "'" ,null);
        db.close();
    }
}
