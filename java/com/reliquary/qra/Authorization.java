package com.reliquary.qra;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Nikhil Yadav on 3/24/2016.
 */
public class Authorization extends Activity implements View.OnClickListener {
    EditText name,password,confirmpassword,answer1;
    Button createAuthorization;
    Spinner question1;
    int valueCheck=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.authorization);

        initializations();
        createAuthorization.setOnClickListener(this);

        ///////////////////////ITEMS SECURITY QUESTION1/////////////////////////////////////////////////////////////
        String[] que1 = {"What is You favourite Food?","What is the name of your favourite Animal?","What is the name of your pet?"};
        ArrayAdapter<String> setques = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,que1);

        ///////////////////////ITEMS SECURITY QUESTION2/////////////////////////////////////////////////////////////


        question1.setAdapter(setques);


    }

    public void initializations()
    {
        name = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        confirmpassword = (EditText)findViewById(R.id.editText3);
        answer1 = (EditText)findViewById(R.id.editText4);
        //answer2 = (EditText)findViewById(R.id.editText5);
        createAuthorization = (Button)findViewById(R.id.button);
        question1 = (Spinner)findViewById(R.id.spinner);
      //  question2 = (Spinner)findViewById(R.id.spinner2);

    }

    @Override
    public void onClick(View v) {
        if (name.getText().toString().isEmpty())
        {
    Toast.makeText(this,"Enter Name",Toast.LENGTH_LONG).show();
    valueCheck++;
        }
        if (password.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            valueCheck++;
        }
        if (confirmpassword.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter confirmation password",Toast.LENGTH_LONG).show();
            valueCheck++;
        }
        if (answer1.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter First Answer",Toast.LENGTH_LONG).show();
            valueCheck++;
        }
       /* if (answer2.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter Second Answer",Toast.LENGTH_LONG).show();
            valueCheck++;
        }*/

        if (valueCheck==0)
{
    if (confirmpassword.getText().toString().contains(password.getText().toString()))
    {

        SQLiteDatabase db;
        db = openOrCreateDatabase( "attendance.db" , SQLiteDatabase.CREATE_IF_NECESSARY , null);
        try {
            String na,pa,q1,a1,q2,a2;
            na = name.getText().toString();
            pa = password.getText().toString();
            q1 = question1.getSelectedItem().toString();
            a1 = answer1.getText().toString();
         //   q2= question2.getSelectedItem().toString();
         //   a2 = answer2.getText().toString();

            final String CREATE_TABLE_CONTAIN = "CREATE TABLE IF NOT EXISTS Authorization ("

                    + "name TEXT,"
                    + "password text,"
                    + "secureQuestion TEXT,"
                    + "secureAnswer TEXT);";

            db.execSQL(CREATE_TABLE_CONTAIN);

String sql ="INSERT INTO Authorization (name ,password ,secureQuestion ,secureAnswer) VALUES('"+na+"','"+pa+"','"+q1+"','"+a1+"')" ;
            db.execSQL(sql);
            Toast.makeText(this, "DONE", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,Switcher.class);
            startActivity(i);
        }
        catch (Exception e) {
            Toast.makeText(this, "ERROR "+e.toString(), Toast.LENGTH_LONG).show();
            System.out.println("ERROR "+e.toString());
        }

    }
    else
    {
Toast.makeText(this,"INCORRECT PASSWORD",Toast.LENGTH_LONG).show();
        valueCheck=0;
        password.setText("");
        confirmpassword.setText("");
    }
}

else
{
    Toast.makeText(this,"Don't Leave Any field Empty",Toast.LENGTH_LONG).show();
    name.setText("");
    password.setText("");
    confirmpassword.setText("");

    valueCheck=0;
}

    }
}
