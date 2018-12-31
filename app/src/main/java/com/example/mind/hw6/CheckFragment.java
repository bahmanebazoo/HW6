package com.example.mind.hw6;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mind.hw6.model.ListLab;
import com.example.mind.hw6.model.ToDoList;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends DialogFragment {

    private TextView mTextViewMassage;
    private String mStringShowMassage;
    public static final String DELETE_TAG = "delete";
    public static final String SAVE_TAG = "save";

    public static CheckFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();
args.putSerializable(DELETE_TAG,uuid);
        CheckFragment fragment = new CheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CheckFragment() {
        // Required empty public constructor
    }


  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTextViewMassage = container.findViewById(R.id.checkDialogText);
        mTextViewMassage.setText(getText(R.string.deletedialog));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_check,false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ATTENTION").setMessage(R.string.deletedialog)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UUID id = (UUID)getArguments().getSerializable(DELETE_TAG);
                        Toast.makeText(getActivity(),ListLab.getInstance().getToDo(id).getTitle(),Toast.LENGTH_SHORT).show();

                       ListLab.getInstance().removeTask(id);

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
