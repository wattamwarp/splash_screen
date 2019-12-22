package com.example.splash_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    EditText phoneno,msg;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneno= findViewById(R.id.et_phoneno);
        msg=findViewById(R.id.et_description);
        send=findViewById(R.id.btn_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int permissioncheck= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);

               if(permissioncheck== PackageManager.PERMISSION_GRANTED){

                  // mymsg();
                String phone= phoneno.getText().toString().trim();
                String text=msg.getText().toString().trim();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                   SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, text, pi, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
                }
                else
                {
                  ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.SEND_SMS},0 );
                   //mymsg();

               }//end of else


//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again later!",
//                            Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }

            }
        });




    }




    private void mymsg() {

      String phone= phoneno.getText().toString().trim();
      String text=msg.getText().toString().trim();

        SmsManager smsmanager=SmsManager.getDefault();

        smsmanager.sendTextMessage(phone,null,text,null,null);
        Toast.makeText(this,"message sent",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:
                if(grantResults.length>=0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    mymsg();
                }//end of if
                else
                {
                    Toast.makeText(this,"you didnt given permission",Toast.LENGTH_SHORT).show();

                }
                break;



        }


    }
}
