package com.example.mind.hw6;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mind.hw6.model.Repository;
import com.example.mind.hw6.model.Task;

import java.util.UUID;

public class TaskActivity extends AppCompatActivity {
    public static final String OBJECT_ADDRESS = "object_address";
    private static final String BUTTONADD = "buttonAdd";
    public static final String BACK_OBJECT_ADDRESS = "back_object_address";
    public static final String DELETE_DIALOG = "delete";
    public static final String SAVE_DIALOG = "save";
    public static final String GET_OBJECT = "getobject";
    private int counter = 0;
    private UUID mUUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mUUID = (UUID) getIntent().getSerializableExtra(OBJECT_ADDRESS);


        boolean state = getIntent().getBooleanExtra(BUTTONADD, false);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TaskFragment.newInstance(mUUID, state))
                .commit();


    }


    public static Intent newIntent(Context context, UUID uuid) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(BACK_OBJECT_ADDRESS, uuid);
        return intent;
    }

    public static Intent newIntent(Context context, int code) {

        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(SAVE_DIALOG, code);

        return intent;
    }


    public static Intent newIntent(Context context, UUID uuid, boolean buttonAdd) {

        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(OBJECT_ADDRESS, uuid);
        intent.putExtra(BUTTONADD, buttonAdd);

        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Task task = Repository.getInstance(getApplicationContext()).getTask(mUUID);
        String title = task.getTitle();
        if (title == null || title.equals(""))
            title = " ";
        task.setTitle(title);
        Repository.getInstance(getApplicationContext()).update(task);

    }

    /*  @Override
    public void onBackPressed() {
        Task toDoList = Repository.getInstance(getParent()).getProfileList().get(Repository.getInstance(getParent()).getProfileList().size() - 1);
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
