package com.example.hcservices;

import android.content.Context;
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

public class Singup extends AppCompatActivity {
    EditText name, address, phoneno, username, password;
    Spinner jobcategory;
    Button btnAdd;
    SQLiteDatabase db;


    String categorey[]={"AC Service","Electrican","Carpenter","Plumber"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        name = (EditText) findViewById(R.id.e1);
        address = (EditText) findViewById(R.id.e2);
        phoneno = (EditText) findViewById(R.id.e3);
        username = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText3);
        jobcategory = (Spinner) findViewById(R.id.s1);
        btnAdd = (Button) findViewById(R.id.button);



        ArrayAdapter<String> dataada=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categorey);
        jobcategory.setAdapter(dataada);

        db = openOrCreateDatabase("serviceDB", Context.MODE_PRIVATE, null);
        if (db != null) {
            Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show();
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS service(name VARCHAR,address VARCHAR,phoneno VARCHAR,username VARCHAR,password VARCHAR,catagory VARCHAR);");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().trim().length() == 0 ||
                        address.getText().toString().trim().length() == 0 ||
                        phoneno.getText().toString().trim().length() == 0 ||
                        username.getText().toString().trim().length() == 0 ||
                        password.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                int pos=jobcategory.getSelectedItemPosition();
                String Text=categorey[pos].toString();
                db.execSQL("INSERT INTO service VALUES('" + name.getText() + "','" + address.getText() +
                        "','" + phoneno.getText() + "','" + username.getText() + "','" + password.getText() + "','"+Text+"');");
                showMessage("Success", "Record added");
                clearText();
            }
        });
    }


    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        name.setText("");
        address.setText("");
        phoneno.setText("");
        username.setText("");
        password.setText("");
        name.requestFocus();

    }
}