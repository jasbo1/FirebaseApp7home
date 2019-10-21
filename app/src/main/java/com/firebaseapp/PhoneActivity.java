package com.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText edit_c_code;
    private EditText edit_new_code;
    Button button_confirm;
    Button button_code;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private boolean isCodeSent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editPhone = findViewById(R.id.editPhone);
        edit_c_code = findViewById(R.id.edit_c_code);
        edit_new_code = findViewById(R.id.edit_new_code);
        button_confirm = findViewById(R.id.button_confirm);
        button_code = findViewById(R.id.button_code);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted");
                if (isCodeSent) {

                    signIn(phoneAuthCredential);

                } else {
                   // signIn(phoneAuthCredential);
                }


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed" + e.getMessage());


            }



            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                isCodeSent = true;
                //показать view для ввода кода смс
                if(isCodeSent) {
                    button_confirm.setVisibility(View.GONE);
                    editPhone.setVisibility(View.GONE);
                    edit_new_code.setVisibility(View.VISIBLE);
                    button_code.setVisibility(View.VISIBLE);
                    Log.e("ololo", "onCodeSent()");
                }
            }
           @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };

    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance()
                .signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PhoneActivity.this, "Успешно", Toast.LENGTH_SHORT).show();


                           button_code.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   //String s = editText2.getText().toString().trim();
                                   if (edit_new_code.length() == 6) {
                                       startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                                       Log.e("ololo", "signIn(phoneAuthCredential) in btnConfirmCode");
                                   } else {
                                       Toast.makeText(PhoneActivity.this, "Введите код!", Toast.LENGTH_SHORT);
                                       Log.e("ololo", "Toast");

                                   }
                               }
                           });

                            } else {
                            Log.e("TAG", "Ошибка авторизации");
                        }

                    }
                });
    }

    public void onClick(View view) {
        String countryCode = edit_c_code.getText().toString().trim();
        String phone = countryCode + editPhone.getText().toString().trim();
        Log.d("TAG", "onClick: " + view);

        if (TextUtils.isEmpty(phone)) {
            editPhone.setError("Укажите номер телефона");
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                30,
                TimeUnit.SECONDS,
                this,
                callbacks);
        button_confirm.setVisibility(View.GONE);
        button_code.setVisibility(View.GONE);
        editPhone.setVisibility(View.GONE);
        edit_c_code.setVisibility(View.GONE);


        button_code.setVisibility(View.VISIBLE);
        edit_new_code.setVisibility(View.VISIBLE);


        button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                




            }
        });
    }
    }





