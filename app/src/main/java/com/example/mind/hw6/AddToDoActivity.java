package com.example.mind.hw6;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class AddToDoActivity extends AppCompatActivity {
    public static final String OBJECT_ADDRESS = "object_address";
    private static final String BUTTONADD = "buttonAdd";
    public static final String BACK_OBJECT_ADDRESS = "back_object_address";
    public static final String DELETE_DIALOG = "delete";
    public static final String SAVE_DIALOG = "save";
    public static final String GET_OBJECT = "getobject";
    private int counter=0;
    private UUID mUUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        FragmentManager fragmentManager = getSupportFragmentManager();
         mUUID= (UUID) getIntent().getSerializableExtra(OBJECT_ADDRESS);


        // Toast.makeText(getApplicationContext(),id.toString(),Toast.LENGTH_LONG).show();
        boolean state = getIntent().getBooleanExtra(BUTTONADD, false);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddToDoFragment.newInstance(mUUID, state))
                .commit();


    }


    public static Intent newIntent(Context context, UUID uuid) {
        Intent intent = new Intent(context, AddToDoActivity.class);
        intent.putExtra(BACK_OBJECT_ADDRESS, uuid);
        return intent;
    }
    public static Intent newIntent(Context context, int code){

        Intent intent = new Intent(context, AddToDoActivity.class);
        intent.putExtra(SAVE_DIALOG,code);

        return intent;
    }


    public static Intent newIntent(Context context, UUID uuid, boolean buttonAdd) {

        Intent intent = new Intent(context, AddToDoActivity.class);
        intent.putExtra(OBJECT_ADDRESS, uuid);
        intent.putExtra(BUTTONADD, buttonAdd);

        return intent;
    }

  /*  @Override
    public void onBackPressed() {
        ToDoList toDoList = Repository.getInstance(getParent()).getProfileList().get(Repository.getInstance(getParent()).getProfileList().size() - 1);
        *//*if(Repository.getInstance().getProfileList().size()==1&&counter==1){
            Repository.getInstance().getProfileList().remove(0);
            super.onBackPressed();
            counter=0;
        }*//*


        if (toDoList.getTitle() == null||toDoList.getTitle().equals("")&&counter==0) {
            Toast.makeText(this, "your Title is Empty!!", Toast.LENGTH_LONG).show();
            counter++;
        } else {
            super.onBackPressed();

        }
    }*/
}
