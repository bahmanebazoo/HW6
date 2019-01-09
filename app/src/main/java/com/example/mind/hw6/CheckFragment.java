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

    AddToDoFragment addToDoFragment;
    public static final String DELETE_TAG = "delete";
    UUID id;

    public static CheckFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
        args.putSerializable(DELETE_TAG, uuid);
        CheckFragment fragment = new CheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CheckFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ATTENTION").setMessage(R.string.deletedialog)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id = (UUID) getArguments().getSerializable(DELETE_TAG);
                        Toast.makeText(getActivity(), Repository.getInstance(getActivity()).getTask(id).
                                getTitle() + " deleted", Toast.LENGTH_SHORT).show();
                        Repository.getInstance(getActivity()).removeTask(id);


                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        addToDoFragment.processOfDelete(Repository.getInstance(getActivity()).getTask(id).getUserUUID());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addToDoFragment = (AddToDoFragment) getTargetFragment();

    }
}
