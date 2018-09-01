package com.reliquary.qra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by NIKE on 4/3/2015.
 */
public class Database extends SQLiteOpenHelper {

    public static final String Databasename = "attendance.db";
    public static final String Title = "title";
    public static final String Body = "body";
    public static final String Col = "color";
    public static final int DATABASE_VERSION = 1;


    Context context;
    public Database(Context context) {
        super(context, Databasename, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void checkDatabaseVersion() {
        SQLiteDatabase db = this.getWritableDatabase();

        // if the DATABASE_VERSION is newer
        //    onUpgrade is called before this is reached
    }
    ///////////////////////////////////////////TITLE LIST///////////////////////////////////////////////////////////////////
    public ArrayList<String> regList()
    {

        ArrayList<String> arr = new ArrayList();
        Database sv = this;
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from commonReg ",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex("roll")));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            //  Toast.makeText(context,"No Notes to Display",Toast.LENGTH_SHORT).show();
        }
        return arr;
    }


    ////////////////////////////////////////////////////////////////BODY LIST/////////////////////////////////////
    public ArrayList<String> showBody()
    {
        ArrayList<String> arr = new ArrayList();
        Database sv = this;
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from EasyPadStorage",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex(Database.Body)));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            //  Toast.makeText(context,"No Notes to Display",Toast.LENGTH_SHORT).show();
        }
        return arr;
    }


    /////////////////////////////////////////////////////////////COLOR LIST//////////////////////////////////////////////////////
    public ArrayList<String> showCol()
    {
        ArrayList<String> arr = new ArrayList();
        Database sv = this;
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from EasyPadStorage",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex(Database.Col)));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            //  Toast.makeText(context,"No Notes to Display",Toast.LENGTH_SHORT).show();
        }
        return arr;
    }

    ////////////////////////////////////////////////////////DELETER////////////////////////////////////////////////////////
    public void deleteThis(String a)
    {

        String string = a;
        Database sv = this;
        SQLiteDatabase sq = sv.getReadableDatabase();
        sq.execSQL("DELETE FROM EasyPadStorage WHERE title="+"'" + string +"'");
    }

    ////////////////////////////////////////////////////////////UPDATER//////////////////////////////////////////////////////////////
    public void updateC(String oldt, String newt,String newb,String newC) {

       try{
           Database sv = this;

        SQLiteDatabase sq = sv.getReadableDatabase();
       /* ContentValues cv = new ContentValues();
        cv.put(Title,""+newt);
        cv.put(Body,""+newb);*/
           sq.execSQL("UPDATE EasyPadStorage SET title='"+newt+"',body='"+newb+"',color='"+newC+"' WHERE title='"+oldt+"'");
    }
    catch (Exception e)
    {}
    }



    public int idreturn()
    {
        int arr=0;
        Database sv = this;
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from EasyPadStorage",null);
        cursor.moveToFirst();
        try {


            do {

                {



                    arr = cursor.getInt(cursor.getColumnIndex("id"));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            //  Toast.makeText(context,"No Notes to Display",Toast.LENGTH_SHORT).show();
        }
        return arr;

    }

}
