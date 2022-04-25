package com.example.finalproject1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finalproject1.Task;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    //versi database
    private static final int DATABASE_VERSION = 1;

    //nama database
    private static final String DATABASE_NAME = "TaskData";

    //table name task
    private static final String TABLE_TASK = "Task";

    //task table collumn
    private static final String KEY_ID = "id";
    private static final String TASK_NAME = "TaskName";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK_NAME + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    //upgrade database ver
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        //create table again
        onCreate(db);
    }

    //operasi crud
    //add
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getTask());


        //insert row
        db.insert(TABLE_TASK, null, values);
        db.close();
    }



    //get all task
    public List<Task> getAllTask(){
        List<Task> taskList= new ArrayList<Task>();
        //select all query
        String selectQuery = "SELECT * FROM " +TABLE_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all row and add to list
        if(cursor.moveToFirst()){
            do{
                Task taskk = new Task();
                taskk.setId(Integer.parseInt(cursor.getString(0)));
                taskk.setTask(cursor.getString(1));


                //add to list
                taskList.add(taskk);
            }while(cursor.moveToNext());
        }
        return taskList;
    }

    //update single task
    public int updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getTask());

        return db.update(TABLE_TASK, values, KEY_ID + " = ?", new String[]{String.valueOf(task.getId())});

    }

    //delete single task
    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, KEY_ID + " = ?", new String[] {String.valueOf(task.getId())});
        db.close();
    }

    //delete all task
    public void deleteAllTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK,null,null);
        db.close();
    }

    //get task count
    public int getTaskCount(){
        String countQuery = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }



}
