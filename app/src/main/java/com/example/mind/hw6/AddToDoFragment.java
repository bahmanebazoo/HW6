package com.example.mind.hw6;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mind.hw6.model.ListLab;
import com.example.mind.hw6.model.ToDoList;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToDoFragment extends Fragment {

    private EditText mTitle;
    private EditText mDescription;
    private CheckBox mDone;
    private DatePicker mDatePicker;
    private Button mSave;
    private Button mNext;
    ToDoList mToDoList;

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
        mDatePicker = view.findViewById(R.id.date_input);
        mSave = view.findViewById(R.id.save_to_list);
        //mNext = view.findViewById(R.id.new_to_do);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mToDoList = new ToDoList(mTitle.getText().toString(), mDescription.getText().toString(), mDone.isChecked());
                     ListLab.getInstance().addToDo(mToDoList);
                Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                    mTitle.setText("");
                    mDescription.setText("");
                    mTitle.setHint(R.string.title_hint);
                    mDescription.setHint(R.string.description_hint);
                    mDone.setChecked(false);




            }
        });

        return view;


    }

}
