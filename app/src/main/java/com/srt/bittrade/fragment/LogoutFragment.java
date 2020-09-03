package com.srt.bittrade.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.srt.bittrade.R;

public class LogoutFragment extends Fragment {


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

       return inflater.inflate(R.layout.activity_logout_fragment ,container,false);
    }
}
