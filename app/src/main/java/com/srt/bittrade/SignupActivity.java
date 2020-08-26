package com.srt.bittrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText et_nameSignup,et_emailSignup , et_passwordSignup;
    private Button btn_signup;
    private FirebaseAuth mAuth;
    private String email , pass ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_emailSignup = findViewById(R.id.et_emailSignup);
        et_passwordSignup = findViewById(R.id.et_passwordSignup);
        btn_signup = findViewById(R.id.btn_signup);
         email = et_emailSignup.getText().toString();
         pass = et_passwordSignup.getText().toString();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startActivity(new Intent(SignupActivity.this , LoginActivity.class));
                        }
                        else {
                            Toast.makeText(SignupActivity.this , "Registartion failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}