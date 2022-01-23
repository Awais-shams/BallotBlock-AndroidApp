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

    public void addNewCandidate(HashMap<String, String> candidate) {
//        For Writing into DB
//        here db has rights for writing to DB
        SQLiteDatabase db = getWritableDatabase();
//        Arranges data in DB type format
        ContentValues contentValues = new ContentValues();
//        Insert Column/Field Names here
//        DB understands contentValues format and not hashMap format
        contentValues.put("_id", candidate.get("_id"));
        contentValues.put("name", candidate.get("name"));
        contentValues.put("electionType", candidate.get("electionType"));
        contentValues.put("designation", candidate.get("designation"));
        contentValues.put("votingEndTime", candidate.get("votingEndTime"));
        contentValues.put("status", candidate.get("status"));

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
    public ArrayList<HashMap<String, String>> getAllCandidates() {
//        create object to read data from db
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HashMap<String, String>> candidateList = new ArrayList<HashMap<String, String>>();

        String query = "SELECT * FROM CANDIDATES ORDER BY _id ASC;";
//        Cursor will execute this query
        Cursor cursor = db.rawQuery(query, null);
//        moveToFirst means DB is not empty
        if (cursor.moveToFirst()) {
            do {
//                Declare HashMap in loop to avoid duplication
                HashMap<String, String> candidate = new HashMap<String, String>();
                candidate.put("_id", cursor.getString(0));
                candidate.put("name", cursor.getString(1));
                candidate.put("electionType", cursor.getString(2));
                candidate.put("designation", cursor.getString(3));
                candidate.put("votingEndTime", cursor.getString(4));
                candidate.put("status", cursor.getString(5));
//                now add hashmap(candidate) to ArrayList(candidateList)
                candidateList.add(candidate);
            }while (cursor.moveToNext());
        }
        db.close();
        return candidateList;
    }

    //    create function to get 1 record for editing
    public HashMap<String, String> getSingleRecord(String id) {
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, String> singleRecord = new HashMap<String, String>();
        String query = "SELECT * FROM CANDIDATES WHERE _id = '" + id + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            singleRecord.put("_id", cursor.getString(0));
            singleRecord.put("name", cursor.getString(1));
            singleRecord.put("electionType", cursor.getString(2));
            singleRecord.put("designation", cursor.getString(3));
            singleRecord.put("votingEndTime", cursor.getString(4));
            singleRecord.put("status", cursor.getString(5));
        }
        return singleRecord;
    }

    public void DeleteRecord (String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("CANDIDATES","_id = '" + id + "'" ,null);
        db.close();
    }

    public void UpdateRecord (String id, ContentValues contentValues){
        SQLiteDatabase db = getWritableDatabase();
        db.update("CANDIDATES", contentValues,"_id = '" + id + "'" ,null);
        db.close();
    }
}
