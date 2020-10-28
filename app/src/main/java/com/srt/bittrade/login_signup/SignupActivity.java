package com.srt.bittrade.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.srt.bittrade.R;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText et_nameSignup, et_emailSignup, et_passwordSignup , et_Phone_no_signup;
    private Button btn_signup;
    private FirebaseAuth mAuth;
    private FirebaseFirestore Firestore;
    private String email, pass;
    String userID;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        et_emailSignup = findViewById(R.id.et_emailSignup);
        et_passwordSignup = findViewById(R.id.et_passwordSignup);
        btn_signup = findViewById(R.id.btn_signup);
        et_nameSignup= findViewById(R.id.et_nameSignup);
        et_Phone_no_signup = findViewById(R.id.et_Phone_no_Signup);
        mAuth = FirebaseAuth.getInstance();
        Firestore = FirebaseFirestore.getInstance();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


    }

    public void signup() {
        email = et_emailSignup.getText().toString();
        pass = et_passwordSignup.getText().toString();
        final String Name = et_nameSignup.getText().toString();
        final String phone_no = et_Phone_no_signup.getText().toString();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                    userID = mAuth.getCurrentUser().getUid();
                    //Firestore
                    DocumentReference documentReference = Firestore.collection("users").document(userID);
                    final Map<String,Object> user = new  HashMap<>();
                    user.put("Name",Name);
                    user.put("Email",email);
                    user.put("Phone",phone_no);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG","OnSuccess: User profile is created for"+user);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG","onFailure: "+e.toString());
                        }
                    });
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(SignupActivity.this, "Registartion failed", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }


}


/* */