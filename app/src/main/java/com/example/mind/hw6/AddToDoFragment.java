package com.example.mind.hw6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToDoFragment extends Fragment {

    private EditText mTitle;
    private EditText mDescription;
    private CheckBox mDone;
    private Button mSave;
    private Button mNext;

    public AddToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_to_do, container, false);

        mTitle = view.findViewById(R.id.insert_title);
        mDescription = view.findViewById(R.id.insert_description);
        mDone = view.findViewById(R.id.done_condition);
        mSave = view.findViewById(R.id.save_to_list);
        mNext = view.findViewById(R.id.new_to_do);


        return view;


    }

}
