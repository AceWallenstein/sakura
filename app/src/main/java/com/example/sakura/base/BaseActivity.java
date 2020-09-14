package com.example.sakura.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected  final String TAG = getClass().getSimpleName() ;

    //mvp

    public void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
