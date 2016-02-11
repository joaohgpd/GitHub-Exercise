package com.example.joao.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joao.myapplication.R;


public class FragmentRepAdapter extends android.app.Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle saveInstaBundle){
        return inflater.inflate(R.layout.fragment_rep_adapter,container,false);
    }
}
