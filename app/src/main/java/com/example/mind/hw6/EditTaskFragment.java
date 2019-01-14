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
import android.widget.EditText;
import android.widget.Toast;

import com.example.mind.hw6.model.Repository;
import com.example.mind.hw6.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EditTaskFragment extends DialogFragment {
    public static final String GET_ID_FOR_EDIT = "task_id";
    public static final String EDITED_TASK = "com.example.mind.hw6.editedtask";
    public static final int REQ_DATE_TAG = 0;
    public static final int REQ_TIME_TAG = 1;
    public static final String DATE_TAG = "date";
    private MainActivity.PlaceholderFragment mPlaceholderFragment;
    private EditText mTitle;
    private EditText mDescription;
    private CheckBox mDo;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mSave;
    private Button mDelete;
    private Task mTask;
    private UUID id;



    public EditTaskFragment() {
    }

    public static EditTaskFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
        args.putSerializable(GET_ID_FOR_EDIT, uuid);
        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable(GET_ID_FOR_EDIT);
        mTask = Repository.getInstance(getActivity()).getTask(id);
        mPlaceholderFragment = (MainActivity.PlaceholderFragment) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_task, null);
        mTitle = view.findViewById(R.id.insert_title);
        mDescription = view.findViewById(R.id.insert_description);
        mDateButton = view.findViewById(R.id.date_button);
        mDo = view.findViewById(R.id.done_condition);
        mTimeButton = view.findViewById(R.id.time_button);
        mDelete = view.findViewById(R.id.delete_task);


        mTitle.setText(mTask.getTitle());
        mDescription.setText(mTask.getDescription());
        mDo.setChecked(mTask.isDone());
        mDateButton.setText(getFormattedDate("dd-mmm-yyy"));
        mTimeButton.setText(getFormattedDate("hh:mm a"));

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance(getActivity()).removeTask(id);
                Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
                mPlaceholderFragment.updateUI();
            }
        });
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFragment dateFragment = DateFragment.newInstance(mTask.getDate());
                dateFragment.setTargetFragment(EditTaskFragment.this, REQ_DATE_TAG);
                dateFragment.show(getFragmentManager(), DATE_TAG);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeFragment timeFragment = TimeFragment.newInstance(mTask.getDate());
                timeFragment.setTargetFragment(EditTaskFragment.this, REQ_TIME_TAG);
            }
        });

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.edit_task).setPositiveButton(R.string.savetolist, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveMethod();
                mPlaceholderFragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getIntent());
            }
        }).setView(view).create();

    }

    private String getFormattedDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        return dateFormat.format(Date.parse(mTask.getDate().toString()));
    }

    private Intent getIntent() {
        Intent intent = new Intent();
        //intent.putExtra(EDITED_TASK,mTask);
        return intent;
    }



    private void saveMethod() {
        mTask.setTitle(mTitle.getText().toString()+"");
        mTask.setDescription(mDescription.getText().toString());
        mTask.setDone(mDo.isChecked());
        Repository.getInstance(getActivity()).update(mTask);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Date date = (Date) data.getSerializableExtra(DateFragment.EXTRA_DATE);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQ_DATE_TAG) {
            mTask.setDate(date);
            mDateButton.setText(getFormattedDate("dd-mmm-yyyy"));
        }

        if (requestCode == REQ_TIME_TAG) {
            mTask.setDate(date);
            mTimeButton.setText(getFormattedDate("hh:mm a"));
        }
    }
}