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
        insertNote(db, "Part 2", "Transfer sortNotes from Fragment to NoteAdapter", "Begin");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 3", "Fix new bug with null pointer", "Begin");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 4", "2.15 AM. I um hungry. The stoves don't work. ", "Middle");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 5", "3.00 AM. New bug with onCreate when change orientation. ", "Middle");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 6", "3.40 AM. Bug fixed, i don`t now how. ", "Middle");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 7", "4.00 AM. I understand how it`s work and fixed new bugs", "End");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 8", "4.20 AM. I tested app 20 minutes. My friends tested app. i thing god see that how i tested app", "End");
        TimeUnit.SECONDS.sleep(1);
        insertNote(db, "Part 9", "4.30 AM. It`s last message. I do last tests and commit app", "End");
        TimeUnit.SECONDS.sleep(1);

    }
}
