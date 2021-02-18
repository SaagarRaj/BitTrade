package com.srt.bittrade.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.srt.bittrade.MainActivity;
import com.srt.bittrade.R;
import com.srt.bittrade.login_signup.LoginActivity;

import java.util.concurrent.Executor;

import static com.srt.bittrade.R.layout.*;


public class ProfileFragment extends Fragment {
    private TextView TextProfileName , TextProfileEmail , TextProfilePhone;
    private FirebaseAuth mAuth;
    private ImageView profileimg;
    private Button ChangeProfileImg , Logout;
    private FirebaseFirestore fstore;
    String userID ;
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable  ViewGroup container,@Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(activity_profile_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        TextProfileName = view.findViewById(R.id.TextProfileName);
        TextProfileEmail = view.findViewById(R.id.TextProfileEmail);
        TextProfilePhone = view.findViewById(R.id.TextProfilePhone);
        profileimg = view.findViewById(R.id.UserVectorId);
        Logout = view.findViewById(R.id.Logout);
        userID = mAuth.getCurrentUser().getUid();
        ChangeProfileImg = view.findViewById(R.id.ChangeProfileImg);
        final DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot DocumentSnapshot, @Nullable FirebaseFirestoreException e) {
                TextProfilePhone.setText(DocumentSnapshot.getString("phone"));
                TextProfileName.setText(DocumentSnapshot.getString("Fname"));
                TextProfileEmail.setText(DocumentSnapshot.getString("email"));
            }
        });


        ChangeProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent( getContext() ,LoginActivity.class));
            }
        });


        return view;
    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle("Profile");

    }
}
