package com.example.hcservices;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Plumber extends AppCompatActivity {
    SQLiteDatabase db;
    ListView l4;

    ArrayList<String> cat = new ArrayList<String>();
String ar[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);

        l4=findViewById(R.id.l4);


        db = openOrCreateDatabase("serviceDB", Context.MODE_PRIVATE, null);
        if (db != null) {
            //Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
            db.execSQL("CREATE TABLE IF NOT EXISTS service(name VARCHAR,address VARCHAR,phoneno VARCHAR,username VARCHAR,password VARCHAR,catagory VARCHAR);");
        }

        Intent in = getIntent();
        final String cate = in.getStringExtra("cat");

        Cursor c = db.rawQuery("SELECT * FROM service WHERE catagory='"+cate+"';",null);

        StringBuffer bf=new StringBuffer();
        bf.delete(0,bf.length());
        if (c.getCount()!=0){
            while(c.moveToNext()){
                cat.add(c.getString(0)+"\n"+c.getString(1)+"\n"+c.getString(2));
            }
          //  Toast.makeText(this, bf.toString(), Toast.LENGTH_SHORT).show();
        }

      /*  while(c.moveToNext())
         {
             String ca= c.getString(0);
             cat.add(ca);

         }*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cat);
        l4.setAdapter(arrayAdapter);
        l4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = db.rawQuery("SELECT phoneno FROM service WHERE catagory='"+cate+"';",null);
                String ar[ ]=new String[c.getCount()];
                int i=0;
                if(c.getCount()!=0) {
                    while(c.moveToNext()){
                        ar[i]=c.getString(0);
                        i++;
                    }
                    Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ar[position]));
                    startActivity(in);
                }

            }
        });
    }
}
