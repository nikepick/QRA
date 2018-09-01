package com.reliquary.qra;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Nikhil Yadav on 3/28/2016.
 */
public class Forgot extends Activity {
TextView que1;
    EditText ans1;
    Button tell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        /////////////////////////////////////////VIEW INITIALIZATION///////////////////////////////////////////////////
        que1 = (TextView)findViewById(R.id.textView);
       // que2 = (TextView)findViewById(R.id.textView2);
        ans1 = (EditText)findViewById(R.id.editText6);
    //    ans2 = (EditText)findViewById(R.id.editText7);
        tell = (Button)findViewById(R.id.button7);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        que1.setText(secureq1().get(0));
    //    que2.setText(sequreq2().get(0));

        tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ans1.getText().toString().equalsIgnoreCase(sequrea1().get(0)))
                {
                    que1.setText(password().get(0));
                }
            }
        });
    }

/////////////////////////////////////RETURN PASSWORD/////////////////////////////////////////////////////////////
    public ArrayList<String> password()
    {

        ArrayList<String> arr = new ArrayList();
        Database sv = new Database(this);
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from Authorization",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex("password")));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("NO AUTHORIZATION");
            builder.setMessage("PLEASE CREATE AUTHORIZATION");
            builder.show();
        }
        return arr;
    }
    /////////////////////////////////////RETURN SECURE QUESTION ONE/////////////////////////////////////////////////////////////
    public ArrayList<String> secureq1()
    {

        ArrayList<String> arr = new ArrayList();
        Database sv = new Database(this);
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from Authorization",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex("secureQuestion")));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("NO AUTHORIZATION");
            builder.setMessage("PLEASE CREATE AUTHORIZATION");
            builder.show();
        }
        return arr;
    }


    /////////////////////////////////////RETURN SECURE ANS 1/////////////////////////////////////////////////////////////
    public ArrayList<String> sequrea1()
    {

        ArrayList<String> arr = new ArrayList();
        Database sv = new Database(this);
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from Authorization",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex("secureAnswer")));

                }
            } while (cursor.moveToNext());
        }
        catch (Exception e)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("NO AUTHORIZATION");
            builder.setMessage("PLEASE CREATE AUTHORIZATION");
            builder.show();
        }
        return arr;
    }



}
