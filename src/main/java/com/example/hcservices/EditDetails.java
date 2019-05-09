package com.example.hcservices;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditDetails extends AppCompatActivity {
    EditText name, address, phoneno;
    Spinner jobcategory;
    Button btnUpdt;
    SQLiteDatabase db;

    String categorey[]={"AC Service","Electrican","Carpenter","Plumber"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        name = (EditText) findViewById(R.id.edt1);
        address = (EditText) findViewById(R.id.edt2);
        phoneno = (EditText) findViewById(R.id.edt3);
        jobcategory = (Spinner) findViewById(R.id.sp1);
        btnUpdt = (Button) findViewById(R.id.btn1);
        Intent in=getIntent();
        final String user=in.getStringExtra("User");
        final String pass=in.getStringExtra("pass");


        ArrayAdapter<String> ad=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categorey);
        jobcategory.setAdapter(ad);

        db = openOrCreateDatabase("serviceDB", Context.MODE_PRIVATE, null);
        if (db != null) {
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE IF NOT EXISTS service(name VARCHAR,address VARCHAR,phoneno VARCHAR,username VARCHAR,password VARCHAR,catagory VARCHAR);");
        }

   /*Intent usr=getIntent();
    final String uname=usr.getStringExtra("User");*/
   Cursor c=db.rawQuery("SELECT * FROM service WHERE username='"+user+"' and password='"+pass+"'",null);
   //int i=0;


        if(c.moveToNext()) {
            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            name.setText(c.getString(0));
            address.setText(c.getString(1));
            phoneno.setText(c.getString(2));
            jobcategory.setPrompt(c.getString(5));

        }
        else
        {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            db.close();

        }
        btnUpdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos=jobcategory.getSelectedItemPosition();
                String Text=categorey[pos].toString();
                //Toast.makeText(getApplicationContext(), "  "+Text, Toast.LENGTH_SHORT).show();

                db.execSQL("UPDATE service SET name='"+name.getText().toString()+"',address='"+address.getText().toString()+"',phoneno='"+phoneno.getText().toString()+"',catagory='"+Text+"' WHERE username='"+user+"' and password='"+pass+"';");
               if(db!=null) {
                   showMessage("Success", "Record Modified");
                   //clearText();
                  /* Intent i=new Intent(EditDetails.this,servicehome.class);
                   startActivity(i);*/
               }
               else
               {
                   showMessage("Error", "Error");
                   //clearText();
               }
            }
        });
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {
        name.setText("");
        address.setText("");
        phoneno.setText("");
        name.requestFocus();

    }
}


