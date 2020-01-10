package com.example.note.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NotesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "note.db";
    private static final int SCHEMA = 2;
    public static final String TABLE = "note";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_TAGS = "tags";
    public static final String COLUMN_DATE1 = "date1";
    public static final String COLUMN_DATE2 = "date2";

    public NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE  + " TEXT, "
                + COLUMN_BODY  + " TEXT, "
                + COLUMN_TAGS  + " TEXT, "
                + COLUMN_DATE1 + " TEXT,"
                + COLUMN_DATE2 + " TEXT);");

        try {
            init(db);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    private void insertNote(SQLiteDatabase db, String title,
                            String body, String tags) {
        ContentValues notes = new ContentValues();
        notes.put("title", title);
        notes.put("date1", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        notes.put("date2", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        notes.put("body", body);
        notes.put("tags", tags);
        db.insert(TABLE, null, notes);
    }

    private void init(SQLiteDatabase db) throws InterruptedException {
        insertNote(db, "Part 1", "Recreate project for fix bugs", "Begin");
        TimeUnit.SECONDS.sleep(1);
    }
}
