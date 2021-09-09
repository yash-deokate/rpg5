package com.zer0.rpg5.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.zer0.rpg5.Model.Task;
import com.zer0.rpg5.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }


    //alt insert to add contructor and override method

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE "+ Params.TABLE_NAME
                +"("+Params.KEY_ID+" INTEGER PRIMARY KEY, " + Params.KEY_TASK+" TEXT"+")";
        Log.d("dbyash","Query being run: "+create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTask(Task task){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_TASK,task.getTask());

        db.insert(Params.TABLE_NAME,null,values);
        Log.d("dbyash","DB inserted");
        db.close();
    }

    public List<Task> getAllTasks(){
        List<Task> taskList= new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();

        //generatethe query to read from db
        String select="SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor=db.rawQuery(select,null);
        //Loop through now
        if(cursor.moveToFirst()){
            do {
                Task task=new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTask(cursor.getString(1));
                taskList.add(task);
            }while (cursor.moveToNext());
        }
        return taskList;
    }

    public int updateTask(Task task){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_TASK,task.getTask());
        //Lets update now
        return db.update(Params.TABLE_NAME,values,Params.KEY_ID+"=?",
                new String[]{String.valueOf(task.getId())});

    }

    public void deleteTaskByid(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTaskByTask(Task task){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?",new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public int getCount(){
        String query="SELECT * FROM "+Params.TABLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        return  cursor.getCount();
    }
}
