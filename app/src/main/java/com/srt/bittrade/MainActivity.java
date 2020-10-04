package com.srt.bittrade;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.srt.bittrade.fragment.AboutAppFragment;
import com.srt.bittrade.fragment.HomeFragment;
import com.srt.bittrade.fragment.ProfileFragment;
import com.srt.bittrade.login_signup.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.Frame);
        openDashboard();
        currentUser();
        ActionBar();
        setUpToolbar();
        configureNavigationDrawer();
       // openHome();


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
                if(itemID == R.id.AboutApp){
                    fragment = new AboutAppFragment();
                } else if(itemID ==R.id.Home){
                    fragment = new HomeFragment();
                } else if(itemID == R.id.Profile){
                    fragment = new ProfileFragment();
                }else if(itemID == R.id.Logout){
                   Logout();
                }
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


    private void Logout() {
        auth.signOut();
        Toast.makeText(MainActivity.this, "Logout",Toast.LENGTH_SHORT);
        startActivity(new Intent(MainActivity.this , LoginActivity.class));
    }
    private void setUpToolbar(){
        drawerLayout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void ActionBar() {
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable;
        colorDrawable = new ColorDrawable(Color.parseColor("#192537"));
        actionBar.setBackgroundDrawable(colorDrawable);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }

    private void openDashboard(){
        navigationView = findViewById(R.id.navigationView);
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.Frame ,new  HomeFragment()).addToBackStack(null).commit();
        getSupportActionBar().setTitle("Home");
        navigationView.setCheckedItem(R.id.Home);

    }



}