package com.logicaltech.mydemoapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.logicaltech.mydemoapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentShipping extends Fragment {


    public FragmentShipping() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_shipping, container, false);
    }

}
