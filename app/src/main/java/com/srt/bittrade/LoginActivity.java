package com.srt.bittrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Console;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private TextView tv_signup, tv_forgotpass;
    private EditText et_emailLogin, et_passLogin;
    private String email, password;
    private FirebaseAuth mAuth;
    private String emailLogin, passwodLogin;
    private ProgressBar pb_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        tv_signup = findViewById(R.id.tv_signup);
        tv_forgotpass = findViewById(R.id.tv_forgotPass);
        et_emailLogin = findViewById(R.id.et_emailLogin);
        et_passLogin = findViewById(R.id.et_passLogin);
        pb_Login = findViewById(R.id.pb_Login);

        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }


        });


        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


    }

    private void userlogin() {
        emailLogin = et_emailLogin.getText().toString();
        passwodLogin = et_passLogin.getText().toString();

        if (TextUtils.isEmpty(emailLogin)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwodLogin)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        pb_Login.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailLogin,passwodLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pb_Login.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                    pb_Login.setVisibility(View.GONE);
                }
            }
        });

    }


}