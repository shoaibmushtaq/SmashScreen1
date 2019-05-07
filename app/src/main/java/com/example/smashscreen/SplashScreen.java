package com.example.smashscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity
{


    //permission code
    public static final int PERMISSION_REQUEST_CODE=1240;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //checking if the permissions are already granted
        if (ContextCompat.checkSelfPermission(SplashScreen.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(SplashScreen.this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(SplashScreen.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();

            Thread thread=new Thread(){

                @Override
                public void run() {

                    try {
                        sleep(5000);

                        intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();

            //if permissions are not granted before then invoke reuquestStoragePermission method
        } else {
            requestStoragePermission();
        }
    }

    //this method is used if the user have already deny the permission and gives user the custom dialog
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
        ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("permissions are needed ")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashScreen.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .create().show();

            //
        } else {
            ActivityCompat.requestPermissions(SplashScreen.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }



    //this method is used to get allow request from user and check
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE)  {
            if ((grantResults.length > 0) &&(grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

                Thread thread=new Thread(){

                    @Override
                    public void run() {

                        try {
                            sleep(5000);

                            intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();




            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                finish();
            }


        }
    }







}
