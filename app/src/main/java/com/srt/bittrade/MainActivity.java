package com.srt.bittrade;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.srt.bittrade.login_signup.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private FrameLayout frameLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.Frame);

        //toolbar = findViewById(R.id.Toolbar);
        currentUser();
        configureNavigationDrawer();








    }

    private void currentUser() {

        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }


            }
        };
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemID = item.getItemId();



                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.Frame, fragment); // replace a Fragment with Frame Layout
                    transaction.commit(); // commit the changes
                    drawerLayout.closeDrawers(); // close the all open Drawer Views
                    return true;
                }


                return false;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ActionBar() {
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable;
        colorDrawable = new ColorDrawable(Color.parseColor("#192537"));
        actionBar.setBackgroundDrawable(colorDrawable);


    }
}