package com.example.ritesh.sqllitestudentrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME="Mystudent.db";
    public final static String TABLE_NAME="mystudent_table";
    public final static String COL_1="ID";
    public final static String COL_2="NAME";
    public final static String COL_3="EMAIL";
    public final static String COL_4="COURSE_COUNT";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +TABLE_NAME+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT," +
                " EMAIL TEXT," +
                " COURSE_COUNT INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);



    }

    public boolean insertData(String name,String email,String courseCount){

        SQLiteDatabase databaseobject=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,courseCount);

       long result= databaseobject.insert(TABLE_NAME,null,contentValues);

       if (result ==-1){
           return false;
       }
       else
       {
           return true;
       }

    }

    public boolean updateData(String id,String name,String email,String courseCount){

        SQLiteDatabase sqLiteDatabaseObject=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,courseCount);

        sqLiteDatabaseObject.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }

    public Cursor getData(String id){

        SQLiteDatabase sqLiteDatabaseObject=this.getWritableDatabase();

        String query="Select * from "+TABLE_NAME+" WHERE ID='"+id+"'";

        Cursor cursor=sqLiteDatabaseObject.rawQuery(query,null);
        return cursor;
    }

    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabaseObject=this.getWritableDatabase();

        return  sqLiteDatabaseObject.delete(TABLE_NAME,"ID=?",new String[]{id});
    }

    public Cursor getAllData(){

        SQLiteDatabase sqLiteDatabaseObject=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabaseObject.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}
