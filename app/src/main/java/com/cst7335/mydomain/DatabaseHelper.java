package com.cst7335.mydomain;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DatabaseHelper class extends SQLiteOpenHelper
public class DatabaseHelper extends SQLiteOpenHelper {
    // Declare DATABASE_NAME and DATABASE_VERSION
    private static final String DATABASE_NAME = "scoresDB";
    private static final int DATABASE_VERSION = 1;

    // DatabaseHelper constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Override the onCreate method
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the scores table
        String createTableQuery = "CREATE TABLE scores (id INTEGER PRIMARY KEY, score INTEGER)";
        // Execute the create table query
        db.execSQL(createTableQuery);
    }

    // Override the onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the scores table if it exists
        db.execSQL("DROP TABLE IF EXISTS scores");
        // Call the onCreate method
        onCreate(db);
    }

    // Save the score to the database
    public void saveScore(int score) {
        // Get the writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new ContentValues object
        ContentValues values = new ContentValues();
        // Put the score in the ContentValues object
        values.put("score", score);
        // Insert the score into the scores table
        db.insert("scores", null, values);
    }

    // Get the top score from the database
    @SuppressLint("Range")
    public int getTopScore() {
        // Get the readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the scores table for the top score
        Cursor cursor = db.rawQuery("SELECT * FROM scores ORDER BY score DESC LIMIT 1", null);
        // Move the cursor to the first row
        if (cursor.moveToFirst()) {
            // Return the score
            return cursor.getInt(cursor.getColumnIndex("score"));
        }
        return 0;
    }
}
