package com.example.mind.hw6;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mind.hw6.model.ListLab;
import com.example.mind.hw6.model.ToDoList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToDoFragment extends Fragment {

    private static final String DIALOG_TAG = "DialogDate";
    private static final int REQ_DATE_PICKER = 0;
    public static final String DATE_TAG = "date";
    public static final String DELETE_TAG= "delete";


    private EditText mTitle;
    private EditText mDescription;
    private String mStringTitle;
    private String mStringDescription;
    private CheckBox mDo;
   // private DatePicker mDatePicker;
    private Button mDateButton;
    private Button mSave;
    private Button mDelete;
    ToDoList mToDoList;
    private UUID id;
    private boolean state;
    private boolean mBooleancheck;

    private static final String ARG_BUTTON = "put_section_number";
    public static final String ARG_UUID_KEY = "get_uuid";

    public AddToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (UUID) getArguments().getSerializable(ARG_UUID_KEY);
//        Toast.makeText(getActivity(),id.toString(),Toast.LENGTH_LONG).show();
        state = getArguments().getBoolean(ARG_BUTTON);
        mToDoList = ListLab.getInstance().getToDo(id);
        mStringTitle = ListLab.getInstance().getToDo(id).getTitle();
mStringDescription=ListLab.getInstance().getToDo(id).getDescription();
    }

    public static AddToDoFragment newInstance(UUID uuid, boolean buttonAdd) {
        AddToDoFragment fragment = new AddToDoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_UUID_KEY, uuid);
        args.putBoolean(ARG_BUTTON, buttonAdd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        View view = inflater.inflate(R.layout.fragment_add_to_do, container, false);
        mTitle = view.findViewById(R.id.insert_title);
        mDescription = view.findViewById(R.id.insert_description);
        mDo = view.findViewById(R.id.done_condition);
       // mDatePicker = view.findViewById(R.id.date_input);
        mDateButton =view.findViewById(R.id.date_button);
        mSave = view.findViewById(R.id.save_to_list);
        mDelete = view.findViewById(R.id.new_to_do);


        if (!state) {
            mDelete.setVisibility(View.GONE);
        } else {
            addToFragmentLayout(mToDoList);
        }

//setDateButton();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("onclick", "clicked");

                if (!mTitle.getText().toString().equals("")) {
                    //Toast.makeText(getActivity(),mTitle.getText().toString(),Toast.LENGTH_LONG).show();
                    mToDoList.setTitle(mTitle.getText().toString());
                    mToDoList.setDescription(mDescription.getText().toString());
                    mToDoList.setDone(mDo.isChecked());
                    mToDoList=new ToDoList();
                    ListLab.getInstance().mAddToDo(mToDoList);
                    mTitle.setText(null);
                    mDescription.setText(null);
                    mTitle.setHint(R.string.title_hint);
                    mDescription.setHint(R.string.description_hint);
                    mDo.setChecked(false);
                    mDelete.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"you cant left Title Empty!!",Toast.LENGTH_LONG).show();
                }

               /* ListLab.getInstance().addToDo(mToDoList);
                Log.d("onclick", "object created");
                Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                mTitle.setText("");
                mDescription.setText("");
                mTitle.setHint(R.string.title_hint);
                mDescription.setHint(R.string.description_hint);
                mDo.setChecked(false);*/

            }
        });
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DateFragment fragment = DateFragment.newInstance();
                fragment.setTargetFragment(AddToDoFragment.this,0);
                fragment.show(getFragmentManager(),DATE_TAG);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckFragment fragment = CheckFragment.newInstance(id);
                fragment.setTargetFragment(AddToDoFragment.this,0);
                fragment.show(getFragmentManager(),DELETE_TAG);
                if(mToDoList==null)
                    Toast.makeText(getActivity(),"no object",Toast.LENGTH_SHORT);

               /* ListLab.getInstance().removeTask(id);
                if(ListLab.getInstance().getList().size()!=0) {
                    processOfDelete();
                }
                else{
                    mToDoList =new ToDoList();
                    ListLab.getInstance().mAddToDo(mToDoList);
                    id=mToDoList.getUUID();
                    addToFragmentLayout(mToDoList);
                }*/
            }
        });

        if(true){
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
if(mStringTitle ==null|| mStringTitle .equals("")&&mToDoList.getTitle()!=null&&mToDoList.getTitle().equals("")){
    mStringTitle =mToDoList.getTitle();
}
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToDoList.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(mToDoList.getTitle()==null||mToDoList.getTitle()==""){
                    mToDoList.setTitle(mStringTitle);
                }

            }
        });
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            mToDoList.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });}

        return view;


    }
    private void setDateButton(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        String ouput =format.format(mToDoList.getDate());
        mDateButton.setText(ouput);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode!=0)return;
        if(resultCode==Activity.RESULT_OK){
            Date date = (Date) data.getSerializableExtra(DateFragment.EXTRA_DATE);
            mToDoList.setDate(date);
            setDateButton();


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // getChildFragmentManager().getFragments().get(getArguments().getInt(ARG_SECTION_NUMBER_CHILD)).onActivityResult();
    }

    public void addToFragmentLayout(ToDoList toDoList){
        mTitle.setText(mToDoList.getTitle());
        mDescription.setText(mToDoList.getDescription());
        mDo.setChecked(mToDoList.isDone());
    }

    public void processOfDelete(){
        ToDoList toDoList_this = ListLab.getInstance().getList().get(ListLab.getInstance().getList().size() - 1);
        id = toDoList_this.getUUID();
        mToDoList = ListLab.getInstance().getToDo(id);
        addToFragmentLayout(mToDoList);
    }
}
