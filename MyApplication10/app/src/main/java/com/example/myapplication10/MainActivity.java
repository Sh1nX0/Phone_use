package com.example.myapplication10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private ImageView btnCall;
    private ImageView btnCamera;
    private Button btnVBR;
    private Vibrator vibr;
    private EditText number;
    private EditText message;
    private String toSms;
    private String messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber=findViewById(R.id.phonenumber);
        btnCall=findViewById(R.id.ImgV1);
        btnCamera=findViewById(R.id.ImgV2);
        btnVBR=findViewById(R.id.btnVBR);
        vibr=(Vibrator)getSystemService(VIBRATOR_SERVICE);
        btnVBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibr.vibrate(1000);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                    Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},2);
                }
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNumber.getText().toString();
                if(!number.isEmpty()){
                    if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED) {
                        Intent intent=new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+number));
                        startActivity(intent);
                    }
                    else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},1);
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Введите номер",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void smsSend(View v) {
         number=findViewById(R.id.number);
         message=findViewById(R.id.message);
         toSms="smsto:"+number.getText().toString();
         messageText= message.getText().toString();
        Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));

        sms.putExtra("sms_body", messageText);
        startActivity(sms);
    }
}