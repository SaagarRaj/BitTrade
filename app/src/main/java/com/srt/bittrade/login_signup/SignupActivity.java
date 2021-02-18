package com.srt.bittrade.login_signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.srt.bittrade.MainActivity;
import com.srt.bittrade.R;

import java.util.HashMap;
import java.util.Map;

import io.grpc.ClientStreamTracer;

public class SignupActivity extends AppCompatActivity {
    private EditText et_nameSignup, et_emailSignup, et_passwordSignup , et_Phone_no_signup;
    private Button btn_signup;
    private String email, pass;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DocumentReference documentReference;
    FirebaseFirestore firestore ;
    private  ImageView imageView;
    private Uri imageUri;
    private static final int PICK_IMAGE=1;
    UploadTask uploadTask;
    Bitmap bitmap;





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
        progressBar = findViewById(R.id.progressbar_signup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getInstance().getReference("profile image");

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user == null){
                    signup();
                }
                else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });



    }

    private void signup() {
        final String email = et_emailSignup.getText().toString();
        final String pass = et_passwordSignup.getText().toString().trim();
        final String name = et_nameSignup.getText().toString();
        final String phno = et_Phone_no_signup.getText().toString();
        final String[] userID = new String[1];
        if (TextUtils.isEmpty(email)) {
           et_emailSignup.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            et_passwordSignup.setError("Password Required");
            return;
        }
        if(pass.length()<6){
            et_passwordSignup.setError("Password must be >= 6 Character");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                  Toast.makeText(SignupActivity.this,"User Created ",Toast.LENGTH_SHORT).show();
                  userID[0] = mAuth.getCurrentUser().getUid();
                  DocumentReference documentReference = firestore.collection("users").document(userID[0]);
                  Map<String,Object> user = new HashMap<>();
                  user.put("Fname",name);
                  user.put("phone",phno);
                  user.put("email",email);
                  user.put("password",pass);
                  user.put("Wallet",10000);
                  documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Log.d("TAG","onSuccess: User profile is created for "+userID);
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Log.d("TAG","onFailure :: user"+userID+" Exception :"+ e.toString());
                      }
                  });
                  startActivity(new Intent(getApplicationContext(),MainActivity.class));
                  Toast.makeText(SignupActivity.this,"Signup Bonus 10000$",Toast.LENGTH_LONG).show();

                }
                else{
                     Toast.makeText(SignupActivity.this,"Error occured"+ task.getException().toString() , Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE || requestCode == RESULT_OK || data != null || data.getData() != null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }
    }


}
