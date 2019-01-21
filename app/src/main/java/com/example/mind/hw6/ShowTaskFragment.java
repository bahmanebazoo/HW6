package com.example.mind.hw6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mind.hw6.database.Repository;
import com.example.mind.hw6.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTaskFragment extends DialogFragment {

    public static final String TASK_UUID_TAG = "Task_to_show";
    public static final String EXTRA_TASK_ID = "com.example.mind.hw6.taskid";
    private Long mTaskID;
    private Task mTask;
    private TextView mTitleTask;
    private TextView mDescription;
    private Button mDate;
    private Button mTime;
    private CheckBox mISDone;
    private MainActivity.PlaceholderFragment placeHolderFragment;


    public static ShowTaskFragment newInstance(Long task_uuid) {

        Bundle args = new Bundle();
        args.putSerializable(TASK_UUID_TAG, task_uuid);
        ShowTaskFragment fragment = new ShowTaskFragment();

        fragment.setArguments(args);
        return fragment;
    }

    public ShowTaskFragment() {
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // return super.onCreateDialog(savedInstanceState);
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Repository.getInstance(getActivity()).getTask(mTaskID).getTitle()).*/
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_task, null);
        //       mTitleTask = view.findViewById(R.id.show_title);
        mDescription = view.findViewById(R.id.show_description);
        mDate = view.findViewById(R.id.show_date);
        mISDone = view.findViewById(R.id.show_done);
        //   mTitleTask.setText(mTask.getTitle());
        mDescription.setText(mTask.getMDescription());
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy  hh:mm a");
        String date = format.format(Date.parse(mTask.getMDate().toString()));
        mDate.setText(date);
        mISDone.setClickable(false);
        mISDone.setChecked(mTask.getMDone());


        return new AlertDialog.Builder(getActivity())
                .setTitle(Repository.getInstance(getActivity()).getTask(mTaskID).getMTitle())
                .setPositiveButton(R.string.edit_butoon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_TASK_ID, mTaskID);
                        placeHolderFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                }).setView(view)
                .create();
    }


   /* private void setClickableOpposite() {
        boolean action;
        if (mTitleTask.isClickable())
            action = false;
        else
            action = true;
        mDate.setClickable(action);
        mDescription.setClickable(action);
        mTitleTask.setClickable(action);
        mISDone.setClickable(action);
    }*/
   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       mTaskID =  getArguments().getLong(TASK_UUID_TAG);
       mTask = Repository.getInstance(getActivity()).getTask(mTaskID);
       placeHolderFragment = (MainActivity.PlaceholderFragment) getTargetFragment();
   }

}
