package com.reliquary.qra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener, AdapterView.OnItemClickListener {

    private QRCodeReaderView mydecoderview;
    private ArrayList<String> check,errorCheck,regStu;

    ListView lv;
    String colCode,brCode,ranDom,rollNumber;
    String tablename;
    int curYear,initYear,curMonth,classCode;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//////////////////////////////////VIEW INITIALIZATION////////////////////////////////////////////////////
        lv = (ListView)findViewById(R.id.barlist);
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);
        check = new ArrayList<String>();
///////////////////////////////////////////SQL DATABASE INITIALIZATION///////////////////////////////////

        Database d = new Database(this);

        db = d.getReadableDatabase();

/////////////////////////////////////////////COMMON FOR ALL DATABASE CREATION////////////////////////////
        try {
            db = openOrCreateDatabase("attendance.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

            final String C= "CREATE TABLE IF NOT EXISTS commonReg ("

                    + "roll TEXT);";
            db.execSQL(C);
            db = d.getWritableDatabase();
        }catch (Exception e)
        {
            errorList(e.toString());
        }

/////////////////////////////////////////TODAY'S MONTH//////////////////////////////////////////////////

        Calendar c = Calendar.getInstance();
        curYear = c.get(Calendar.YEAR);
        curMonth = c.get(Calendar.MONTH);

///////////////////FLOATING ACTION BUTTON/////////////////////////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Registration.class);
                startActivity(i);
              /*  Snackbar.make(view, ""+arr, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

    }

//////////////////////////////////////////////QR SCANNER ACTIONS//////////////////////////////////////////////
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        //////////////////////////////calling registered Students//////////////////////////////////////

        registeredStudents();
        AlertDialog.Builder Abuilder = new AlertDialog.Builder(this);


        ////////////////////////////////Checking if scanned code belongs to list///////////////////
        try {
            if (!check.contains(text)) {
                check.add(text);




//////////////////////////////////////////////SPLITTING QRCODE////////////////////////////////////
                    String[] arr = text.split(" ");
                    ArrayList qrSplit = new ArrayList();
                    String[] i = arr;
                    int qrTextlength = arr.length;

                    for (int var = 0; var < qrTextlength; ++var) {
                        String ss = i[var];
                        qrSplit.add(ss);
                    }
///////////////////////////////////////COLLECTING SPLITTED PARTS IN VARIABLES////////////////////////////////////////////
                    colCode = qrSplit.get(0).toString();
                    brCode = qrSplit.get(1).toString();
                    initYear = Integer.parseInt(qrSplit.get(2).toString());
                    ranDom = qrSplit.get(3).toString();
                    classCode = Integer.parseInt(qrSplit.get(4).toString());
                    rollNumber = qrSplit.get(0).toString() + qrSplit.get(1).toString() + qrSplit.get(2).toString() + qrSplit.get(3).toString() + qrSplit.get(4).toString();
                    tablename = tablename(initYear, curYear, curMonth, brCode);

                if (regStu.contains(rollNumber)) {
// ///////////////////////////////////UPDATING TOTAL ATTENDANCE COUNT////////////////////////////////////////////////////////////////
                    try {
                        Database d = new Database(this);
                        SQLiteDatabase db = d.getReadableDatabase();
                        ArrayList at = new ArrayList();
                        at = totalAttendance(rollNumber);
                        int index = 0;
                        /*if(at.contains(rollNumber))
                        {
                            index = at.indexOf(rollNumber);
                        }
                        else
                        {

                        }*/

                        int increment = Integer.parseInt(at.get(index).toString());


                        increment++;
////////////////////////////////////////////////ATENDANCE INCREMENTED AND INSERTED BACK///////////////////////////////////////////
                        db.execSQL("UPDATE "+tablename+" SET Total_Attendance ='" + increment + "' WHERE Roll_Number ='" + rollNumber + "'");
                        sucessSound();
                    } catch (Exception e) {
/////////////////////////////////////////////EXCEPTION CHECK ONE///////////////////////////////
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("eXCEPTION FROM one"+e.toString());
                        builder.show();
                    }
               }

            }


            else {


            }
        }
////////////////////////////////////////////////EXCEPTION CHECK TWO//////////////////////////////////////////
        catch (Exception e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("INVALID QR CODE");
            builder.show();
        }
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }
///////////////////////////////////////////////////LIST ITEM CLICK/////////////////////////////////////////////////
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public String tablename(int iyear,int cyear,int cmonth,String bname)
    {

        int semester  = 4 + (((cyear -2000) - iyear) - 1)*12 + cmonth;
        String year,sem,tablename;
        sem="";
        if (semester>=1 && semester <=4)
        {
            sem = "I";
        }
        if (semester>=5 && semester <=9)
        {
            sem = "II";
        }
        if (semester>=10 && semester <=16)
        {
            sem = "III";
        }
        if (semester>=17 && semester <=21)
        {
            sem = "IV";
        }
        if (semester>=22 && semester <=28)
        {
            sem = "V";
        }
        if (semester>=29 && semester <=33)
        {
            sem = "VI";
        }
        if (semester>=34 && semester <=40)
        {
            sem = "VII";
        }
        if (semester>=42 && semester <=45)
        {
            sem = "VIII";
        }
        year = ""+iyear;
        Toast.makeText(this,""+iyear,Toast.LENGTH_LONG).show();

        return bname+year+sem;

    }

    public void runtimeDatabse(String Table_Name)
    {
        Database n = new Database(this);
        SQLiteDatabase db;
        db = openOrCreateDatabase("attendance.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +Table_Name+ " ("

                + "Roll_Number TEXT,"
                + "Total_Attendance INTEGER,"
                + "Period_1 TEXT,"
                + "Period_2 TEXT,"
                + "Period_3 TEXT,"
                + "Period_4 TEXT,"
                + "Period_5 TEXT,"
                + "Period_6 TEXT,"
                + "Period_7 TEXT);";
        db.execSQL(CREATE_TABLE);

        db = n.getWritableDatabase();

        try {
            String sql = "INSERT INTO " + Table_Name + " (Roll_Number ,Total_Attendance ,Period_1 ,Period_2 ,Period_3 ,Period_4,Period_5 ,Period_6 ,Period_7) VALUES('" + rollNumber + "','" + null + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "','" + "" + "')";
            db.execSQL(sql);
         //   Toast.makeText(this, "DONE from runtime dtabase", Toast.LENGTH_LONG).show();
            sucessSound();
        }
        catch (Exception e)
        {
            Toast.makeText(this,""+e.toString(),Toast.LENGTH_LONG).show();
            errorList(e.toString());
        }

        db.close();
    }

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

    public void errorList(String error)
    {
        errorCheck =new ArrayList<>();
        errorCheck.add(error);
        ArrayAdapter<String> setListItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,errorCheck);
        lv.setAdapter(setListItems);
    }

    public void currentEntries()
    {
        ArrayAdapter<String> setListItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,check);
        lv.setAdapter(setListItems);
    }
    //exporting database
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
    ////////////////////////////////////NOTIFICATION AFTER SUCCESSFUL SCAN///////////////////////////////////////////
    public void sucessSound()
    {

 NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
     Intent buttonIntent = new Intent(this, MainActivity.class);
        buttonIntent.putExtra("notificationId", 0);

        Notification n = new Notification.Builder(this)

                .setDefaults(Notification.DEFAULT_SOUND)
                .build();
        n.flags = Notification.FLAG_NO_CLEAR;


        notificationManager.notify(0, n);

    }


    ///////////////////////////////////////REGISTERED STUDENTS LIST////////////////////////////////////////////
    public void registeredStudents()
    {
     Database d = new Database(this);
        regStu = new ArrayList<>();
        regStu = d.regList();

    }
    /////////////////////////////////////////ATTENDANCE COUNT////////////////////////////////////////////////
    public ArrayList<String> totalAttendance(String roll)
    {

        ArrayList<String> arr = new ArrayList();
        Database sv = new Database(this);
        SQLiteDatabase sq = sv.getReadableDatabase();
        Cursor cursor = sq.rawQuery("select * from "+tablename+" WHERE Roll_Number ='" + roll + "'",null);
        cursor.moveToFirst();
        try {


            do {

                {


                    arr.add(cursor.getString(cursor.getColumnIndex("Total_Attendance")));

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
