package com.example.tilt_the_ball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "Game.db";
    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_SCORE = "user_score";


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_ADDRESS + " TEXT ," + COLUMN_USER_SCORE + " TEXT )";


    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        // Inserting Row

        Log.d("values", String.valueOf(values));
        db.insert(TABLE_USER, null, values);
        db.close();
    }




    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};


        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
 public List<User> getAllPlaces(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase = this.getReadableDatabase();
        List<User> placeList = new ArrayList<>();
        String getAll = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = sqLiteDatabase.rawQuery(getAll,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);
                String address = cursor.getString(4);

                placeList.add(new User());
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  placeList;

}
public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_USER, null);
}
    public Cursor gethighscore(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_USER + " ORDER BY " + COLUMN_USER_SCORE + " DESC ", null);
    }

    /*public void updateEmployee(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_USER_NAME,user.getName());
        contentValues.put(DatabaseHelper.COLUMN_USER_EMAIL,user.getEmail());
        contentValues.put(DatabaseHelper.COLUMN_USER_ADDRESS,user.getAddress());
        contentValues.put(DatabaseHelper.COLUMN_USER_PASSWORD,user.getPassword());


        SQLiteDatabase  db= this.getWritableDatabase();
        db.update(TABLE_USER,contentValues,COLUMN_USER_EMAIL + " = ?" , new String[]
                {String.valueOf(user.getEmail())});
    }*/
    public Cursor fetch(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("select * from " + TABLE_USER + " where " + COLUMN_USER_EMAIL + " = "+ " ? " , new String[]{email});
    }
    public int update(String id, String user_nm, String email, String pass, String addr){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, user_nm);
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_PASSWORD, pass);
        contentValues.put(COLUMN_USER_ADDRESS, addr);
        return db.update(TABLE_USER, contentValues, COLUMN_USER_ID + "= ?", new String[] {id});

    }

    public int Scoreupdate(String email, String score){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_SCORE, score);
        return db.update(TABLE_USER, contentValues, COLUMN_USER_EMAIL + "= ?", new String[] {email});

    }


}
