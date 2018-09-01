package com.reliquary.qra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Nikhil Yadav on 3/28/2016.
 */
public class Switcher extends Activity implements View.OnClickListener {

    Button creAuth,regStu,scanNer,exPort,devSup,forgotPass,proCeed;
    EditText passWord;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector);

        creAuth = (Button)findViewById(R.id.button2);
        regStu = (Button)findViewById(R.id.button3);
        scanNer = (Button)findViewById(R.id.button4);
        exPort = (Button)findViewById(R.id.button5);
        devSup = (Button)findViewById(R.id.button6);

        Database d = new Database(this);
        db = d.getReadableDatabase();


        creAuth.setOnClickListener(this);
        regStu.setOnClickListener(this);
        scanNer.setOnClickListener(this);
        exPort.setOnClickListener(this);
        devSup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button2)
        {
            if(isTableExists(db,"Authorization"))
            {

                final EditText input = new EditText(Switcher.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setView(input);
                b.setMessage("ENTER PASSWORD");
                b.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = input.getText().toString();
                        if (authorized().contains(s)) {
                        deleteThis();
                            Intent i = new Intent(Switcher.this, Authorization.class);
                            startActivity(i);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Switcher.this);
                            builder.setTitle("INVALID PASSWORD");
                            builder.setMessage("PLEASE DO NOT TRY TO HAVE INVALID ACCESS");
                            builder.show();
                        }

                    }


                });
                b.setNegativeButton("Forgot Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgot();

                    }
                });

                b.show();


            }
            else
            {
                Intent i = new Intent(Switcher.this, Authorization.class);
                startActivity(i);


            }


        }

        else if (v.getId()==R.id.button3)
        {
            if(isTableExists(db,"Authorization"))
            {
                final EditText input = new EditText(Switcher.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setView(input);
                b.setMessage("ENTER PASSWORD");
                b.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = input.getText().toString();
                        if (authorized().contains(s)) {

                            Intent i = new Intent(Switcher.this, Registration.class);
                            startActivity(i);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Switcher.this);
                            builder.setTitle("INVALID PASSWORD");
                            builder.setMessage("PLEASE DO NOT TRY TO HAVE INVALID ACCESS");
                            builder.show();
                        }

                    }


                });
                b.setNegativeButton("Forgot Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgot();

                    }
                });

                b.show();


            }
            else
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("NO AUTHORIZATION");
                builder.setMessage("PLEASE CREATE AUTHORIZATION");
                builder.show();

            }


        }
        else if (v.getId()==R.id.button4)
        {
            if(isTableExists(db, "Authorization"))
            {
                final EditText input = new EditText(Switcher.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setView(input);
                b.setMessage("ENTER PASSWORD");
                b.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = input.getText().toString();
                        if (authorized().contains(s)) {

                            Intent i = new Intent(Switcher.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Switcher.this);
                            builder.setTitle("INVALID PASSWORD");
                            builder.setMessage("PLEASE DO NOT TRY TO HAVE INVALID ACCESS");
                            builder.show();
                        }

                    }


                });
                b.setNegativeButton("Forgot Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        forgot();

                    }
                });

                b.show();

            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("NO AUTHORIZATION");
                builder.setMessage("PLEASE CREATE AUTHORIZATION");
                builder.show();
            }

        }
        else if (v.getId()==R.id.button5)
        {
            try {
                backupDatabase();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("DATABASE EXPORTED TO INTERNAL MEMORY under the name 'attendence.db' ");
                builder.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (v.getId()==R.id.button6)
        {

        }



    }
    /////////////////////////////////////////EXISTENCE OF TABLE////////////////////////////////////////////////////////////
    boolean isTableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
    /////////////////////////////////////////CHECKING EXISTENCE OF AUTHORIZED PERSONNEL////////////////////////////////////////////////
    public ArrayList<String>  authorized()
    {


        ArrayList<String> arr = new ArrayList();
        Database sv = new Database(this);
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from Authorization", null);
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
/////////////////////////////////////////////////DELETING DATA FROM AUTHORIZATION TABLE BEFORE ENTERING THE AUTHORIZATRION CLASS/////////////////////////////
public void deleteThis()
{


    Database sv = new Database(this);
    SQLiteDatabase sq = sv.getReadableDatabase();
    sq.execSQL("DROP TABLE IF EXISTS Authorization" );
}
////////////////////////////////////////////////////////forgot password////////////////////////////////////
    public void forgot()
    {
        Intent i = new Intent(this,Forgot.class);
        startActivity(i);
    }
////////////////////////////////////////////////////////EXPORT DATABSE/////////////////////////////////
public static void backupDatabase() throws IOException {
    //Open your local db as the input stream
    String inFileName = "/data/data/com.reliquary.qra/databases/attendance.db";
    File dbFile = new File(inFileName);
    FileInputStream fis = new FileInputStream(dbFile);

    String outFileName = Environment.getExternalStorageDirectory()+"/attendance.db";
    //Open the empty db as the output stream
    OutputStream output = new FileOutputStream(outFileName);
    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = fis.read(buffer))>0){
        output.write(buffer, 0, length);
    }
    //Close the streams
    output.flush();
    output.close();
    fis.close();
}


}
