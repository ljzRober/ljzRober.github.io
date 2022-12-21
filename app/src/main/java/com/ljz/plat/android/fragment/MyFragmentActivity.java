package com.ljz.plat.android.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ljz.plat.android.R;

import java.util.function.Function;

public class MyFragmentActivity extends AppCompatActivity {

    public static final String TAG = "dynamic";
    Button fragment_count;
    Button add;
    Button add2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);
        fragment_count = findViewById(R.id.getFragment_count);
        add = findViewById(R.id.addFragment);
        add2 = findViewById(R.id.addFragment2);
        fragment_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Toast.makeText(getActivity(),
                                String.valueOf(fragmentManager.getBackStackEntryCount()) + "name:"
                                + fragmentManager.getFragments(),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                Fragment fragment1 = new SDynamicFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment1)
                        .commit();            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                Fragment fragment2 = new TDynamicFragment();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, fragment2)
                        .commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = new FDynamicFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, TAG)
//                .hide(fragmentManager.findFragmentById(R.id.inflate_fragment))
//                .show(fragment)jia
                .commit();
    }

    private FragmentActivity getActivity() {
        return this;
    }
}