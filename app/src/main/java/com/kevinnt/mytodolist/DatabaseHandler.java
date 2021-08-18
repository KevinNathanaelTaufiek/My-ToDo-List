package com.kevinnt.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String TABLE_TASK = "TASK";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TODO = "TODO";
    public static final String COLUMN_ISCHECKED = "ISCHECKED";

    public DatabaseHandler(@Nullable Context context) {
        super(context, "MyToDoList.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TASK + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TODO + " TEXT, "
                + COLUMN_ISCHECKED + " BOOL)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Item> getAllTask(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_TASK;

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Item> allTask = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Item i = new Item(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2) == 1 ? true : false);
                allTask.add(i);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return allTask;
    }

    public boolean addTask(Item item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO, item.getTodoText());
        contentValues.put(COLUMN_ISCHECKED, item.isChecked());

        long insert = db.insert(TABLE_TASK, null, contentValues);
        db.close();

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }


    }

    public void updateCheckItem(Item item, Context context){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_TASK
                + " SET " + COLUMN_ISCHECKED + " = " + (item.isChecked() ? 1 : 0)
                + " WHERE " + COLUMN_ID + " = " + item.getId();
        db.execSQL(query);
        db.close();
//        Toast.makeText(context, "MASUK UPDATENYA", Toast.LENGTH_SHORT).show();
    }

    public void deleteTask(Item item){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_TASK
                + " WHERE " + COLUMN_ID + " = " + item.getId();
        db.execSQL(query);
        db.close();
    }

}
