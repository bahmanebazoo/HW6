package com.example.mind.hw6;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.mind.hw6.model.Repository;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends DialogFragment {

    private TaskFragment taskFragment;
    public static final String DELETE_TASK_TAG = "delete";
    public static final String DELETE_ALL_TAG = "delete_all";
    private UUID id;
    private UUID userUUID;

    public static CheckFragment newInstance(UUID uuid, UUID user_id) {

        Bundle args = new Bundle();
        args.putSerializable(DELETE_TASK_TAG, uuid);
        args.putSerializable(DELETE_ALL_TAG, user_id);
        CheckFragment fragment = new CheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CheckFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable(DELETE_TASK_TAG);
        userUUID = (UUID) getArguments().getSerializable(DELETE_ALL_TAG);
        if (id != null) {
            taskFragment = (TaskFragment) getTargetFragment();
        } else {

        }

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String massage;
        if (id != null)
            massage = getString(R.string.deletetaskdialog);
        else
            massage = getString(R.string.deletealldialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ATTENTION").setMessage(massage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (id != null) {
                            Toast.makeText(getActivity(), Repository.getInstance(getActivity()).getTask(id).
                                    getTitle() + " deleted", Toast.LENGTH_SHORT).show();
                            //taskFragment.processOfDelete(id);
                            Repository.getInstance(getActivity()).removeTask(id);

                        } else {
                            Repository.getInstance(getActivity()).removeTasks(userUUID);
                          //  MainActivity.updateAA();
                        }


                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


}
