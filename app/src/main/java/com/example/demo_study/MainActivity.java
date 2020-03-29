package com.example.demo_study;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.example.demo_study";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data();
        Trsanfer(1);
    }

    public void Data(){
        Log.e(TAG,"Open");
    }

    public int Trsanfer(int data){
        Log.e(TAG,"Trsanfer");
        return 0;
    }
}
