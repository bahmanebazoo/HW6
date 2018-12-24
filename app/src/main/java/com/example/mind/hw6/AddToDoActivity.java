package com.example.mind.hw6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment = new AddToDoFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }


    }

    public static Intent newIntent(Context context){

        Intent intent = new Intent(context, AddToDoActivity.class);

        return intent;

    }
}
