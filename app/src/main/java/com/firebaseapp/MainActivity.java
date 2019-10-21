package com.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity( new Intent(this,PhoneActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

    }
}
