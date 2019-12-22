package com.example.splash_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class spinner extends AppCompatActivity {

    Spinner names;
    TextView selectedname;
    String data="...";
    Button click;
    DatabaseReference db;
    ValueEventListener listner;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        selectedname=findViewById(R.id.tv_companyname);
        names =findViewById(R.id.spinner);

        db= FirebaseDatabase.getInstance().getReference().child("customers");

        list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(spinner.this, android.R.layout.simple_spinner_dropdown_item ,list);
        //int i = 1;
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        names.setAdapter(adapter);
        retrivedata();



        names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String n=parent.getItemAtPosition(position).toString();
                String cust_id= String.valueOf(position+1);

                 selectedname.setText("the cust id is"+n);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void retrivedata(){

        listner=db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  int i=0;
                String id = null;
                for (DataSnapshot cust : dataSnapshot.getChildren()) {
                    String company = cust.child("name").getValue(String.class);
                    id = cust.child("id").getValue().toString().trim();
                    list.add(company);
                    //String n= names.getSelectedItem().toString();
                    i++;
                    id= String.valueOf(i);
                    selectedname.setText("the companis are"+id);
                }
                selectedname.setText(id);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}
