package com.example.apploginapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "AppLoginPass";
    public static final String FULLUSERNAME = "user";
    private Button btnExit;
    private SharedPreferences sPref;
    private static final String MY_PREF = "MY_PREF";

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
                    saveFullUserName(fullUserName);
                    infoTextView.setText("Hello, "+fullUserName+"!");

                }
                else{
                    Log.e(TAG, "ActivityResultLauncher: somre error occuried");
                }
            });

    void saveFullUserName(String fullUserName){
        sPref = this.getSharedPreferences(MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(FULLUSERNAME,fullUserName);
        ed.commit();
    }
    void loadUserNameFromMyPref(){
        sPref = this.getSharedPreferences(MY_PREF, MODE_PRIVATE);
        String fullUserName = sPref.getString(FULLUSERNAME,"");
        if(fullUserName.isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            someActivityResultLauncher.launch(intent);
        } else{
            infoTextView.setText("Hello, "+fullUserName+", from mypref!");
        }
    }

    @Override
    public void onClick(View view) {
        sPref = this.getSharedPreferences(MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.remove(FULLUSERNAME);
        ed.commit();
        loadUserNameFromMyPref();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTextView = findViewById(R.id.infoTextView);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

        loadUserNameFromMyPref();
    }
}