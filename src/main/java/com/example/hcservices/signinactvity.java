package com.example.hcservices;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signinactvity extends AppCompatActivity {
    EditText uname,pass;
    Button login;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinactvity);
        uname=(EditText)findViewById(R.id.e1);
        pass=(EditText)findViewById(R.id.e2);
        login=(Button)findViewById(R.id.b1);
        db=openOrCreateDatabase("serviceDB", Context.MODE_PRIVATE,null);
        if(db!=null) {
            Toast.makeText(this, "create ", Toast.LENGTH_SHORT).show();
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS service(name VARCHAR,address VARCHAR,phoneno VARCHAR,username VARCHAR,password VARCHAR,catagory VARCHAR);");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=db.rawQuery("SELECT * FROM service WHERE username='"+uname.getText()+"' and password='"+pass.getText()+"'",null);
                if(c.moveToFirst())
                {
                    showMessage("success", " login sucess!");
                    Intent in = new Intent(signinactvity.this, servicehome.class);
                    in.putExtra("Username",uname.getText().toString());
                    in.putExtra("pass",pass.getText().toString());
                    ClearText();
                    startActivity(in);

                    return;
                }
                else
                {
                    showMessage("Error", "Invalid login!");
                    ClearText();
                    return;
                }
            }
        });

        }
        public void showMessage(String title,String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();
        }
        public void ClearText() {
            uname.setText("");
            pass.setText("");
            uname.requestFocus();
        }

    }

