package com.example.hcservices;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class servicehome extends AppCompatActivity {
    Button edit,delete;
    SQLiteDatabase db;
    String User,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicehome);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edit=findViewById(R.id.btn2);
        delete=findViewById(R.id.btn1);
        Intent in=getIntent();
        User=in.getStringExtra("Username");
        pass=in.getStringExtra("pass");

        db=openOrCreateDatabase("serviceDB",MODE_PRIVATE,null);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(servicehome.this, EditDetails.class);
                in.putExtra("User",User);
                in.putExtra("pass",pass);
                Toast.makeText(getApplicationContext()," "+User,Toast.LENGTH_SHORT).show();
                startActivity(in);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmessage();

            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
void showmessage()

{
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Confirm");
    builder.setMessage("Are you sure You want to delete this Account?");

    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int which) {
            db.execSQL("delete from service where username='"+User+"' and password='"+pass+"';");
            Toast.makeText(servicehome.this, "Deleted", Toast.LENGTH_SHORT).show();

            dialog.dismiss();
        }
    });

    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            dialog.dismiss();
        }
    });

    AlertDialog alert = builder.create();
    alert.show();
}
}
