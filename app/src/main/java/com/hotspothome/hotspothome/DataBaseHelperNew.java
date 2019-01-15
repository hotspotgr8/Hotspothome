package com.hotspothome.hotspothome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilesh Deokar on 7/9/2015.
 */
public class DataBaseHelperNew extends SQLiteOpenHelper  {

    public static final String TAG = "DataBaseHelperNew";
    // SQLiteDatabase database;
    // table names
    public static final String TABLE_SAVE_OFFLINE_ROOM = "saveOfflineromm";
    public static final String TABLE_SAVE_OFFLINE_ROOM_DETAIL = "saveOfflineDetail";

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "hotspot_home";


    public DataBaseHelperNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table "+TABLE_SAVE_OFFLINE_ROOM_DETAIL+ "(id INTEGER ,status INTEGER,name TEXT,room_id INTEGER)");
        db.execSQL("create table "+TABLE_SAVE_OFFLINE_ROOM + "(number INTEGER ,status TEXT,name TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        switch (oldVersion) {

            case 1:

                db.execSQL("DROP TABLE IF EXISTS "+TABLE_SAVE_OFFLINE_ROOM);
                db.execSQL("DROP TABLE IF EXISTS "+TABLE_SAVE_OFFLINE_ROOM_DETAIL);

                break;

            case 2:

                db.execSQL("DROP TABLE IF EXISTS "+TABLE_SAVE_OFFLINE_ROOM_DETAIL);
                db.execSQL("DROP TABLE IF EXISTS "+TABLE_SAVE_OFFLINE_ROOM);

                break;
        }
        onCreate(db);
    }

    public boolean insertSwitchData(BoardModel  tagsModelList)
    {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("id", tagsModelList.id);
            values.put("name", tagsModelList.name);
            values.put("room_id", tagsModelList.room_id);
            values.put("status",tagsModelList.status);


                long result =   db.insert(TABLE_SAVE_OFFLINE_ROOM_DETAIL, null, values);
                if(result == -1){
                    db.close();
                    return false;
                }else {
                    db.close();
                    return true;
                }


        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }


        return false;
    }

    public boolean insertRoomData(RoomModel  tagsModelList)
    {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("number", tagsModelList.number);
            values.put("name", tagsModelList.RoomName);


            long result =   db.insert(TABLE_SAVE_OFFLINE_ROOM, null, values);
            if(result == -1){
                db.close();
                return false;
            }else {
                db.close();
                return true;
            }


        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }


        return false;
    }

    public List<RoomModel> getRoomData() {

        String countQuery = "SELECT  *  FROM  " + TABLE_SAVE_OFFLINE_ROOM ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        List<RoomModel>list = new ArrayList<>();

        // looping through all rows and adding to list

        try {
            if (cursor.moveToFirst()) {

                do {

                    RoomModel model = new RoomModel();
                    model.RoomName = cursor.getString(cursor.getColumnIndex("name"));
                    model.number= cursor.getInt(cursor.getColumnIndex("number"));


                    list.add(model);

                } while (cursor.moveToNext());
            }
            cursor.close();


        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return list ;
    }

    public List<BoardModel> getSwitchData(int roomNum) {


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from saveOfflineDetail where room_id ="+roomNum,null);
        List<BoardModel>list = new ArrayList<>();

        // looping through all rows and adding to list

        try {
            if (cursor.moveToFirst()) {

                do {

                    BoardModel model = new BoardModel();
                    model.name = cursor.getString(cursor.getColumnIndex("name"));
                    model.id= cursor.getInt(cursor.getColumnIndex("id"));
                    model.room_id= cursor.getInt(cursor.getColumnIndex("room_id"));
                    model.status = cursor.getInt(cursor.getColumnIndex("status"));

                    list.add(model);

                } while (cursor.moveToNext());
            }
            cursor.close();


        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return list ;
    }


    public boolean deleteTable(String tableName)

    {
        SQLiteDatabase db = this.getWritableDatabase();

        try {


           return   db.delete(tableName,"",null)>0;

        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }

    }







}