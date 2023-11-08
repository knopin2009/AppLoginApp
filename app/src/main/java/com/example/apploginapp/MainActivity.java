package com.example.apploginapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "AppLoginPass";
    public static final String FULLUSERNAME = "user";

    private TextView infoTextView;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result->{
                if(result.getResultCode()== Activity.RESULT_OK){
                    //process result data
                    Intent data = result.getData();
                    String fullUserName = data.getStringExtra(LoginActivity.FULLUSERNAME);
                    fullUserName=fullUserName.substring(0, fullUserName.length() - 1);
                    Log.d(TAG, ": "+fullUserName);
                    infoTextView.setText("Hello, "+fullUserName+"!");

                }
                else{
                    Log.e(TAG, "ActivityResultLauncher: somre error occuried");
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTextView = findViewById(R.id.infoTextView);
        Intent intent = new Intent(this, LoginActivity.class);

        someActivityResultLauncher.launch(intent);

    }
}