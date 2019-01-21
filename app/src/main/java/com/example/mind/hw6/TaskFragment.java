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

import com.example.mind.hw6.database.Repository;
import com.example.mind.hw6.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    private static final String DIALOG_TAG = "DialogDate";
    private static final int REQ_DATE_PICKER = 0;
    public static final int REQ_DELETE_PICKER = 1;
    public static final String DATE_TAG = "date";
    public static final String DELETE_TASK_TAG = "delete";



    private EditText mTitle;
    private EditText mDescription;
    private String mStringTitle;
    private String mStringDescription;
    private CheckBox mDo;
    // private DatePicker mDatePicker;
    private Button mDateButton;
    private Button mSave;
    private Button mDelete;
    Task mToDoList;
    private Long mTaskID;
    private Long mUserID;
    private int position;
    private boolean state;
    private boolean mBooleancheck;

    private static final String ARG_BUTTON = "put_section_number";
    public static final String ARG_UUID_KEY = "get_uuid";
    public static final String USER_ID_UUID = "userUUID";

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setId();
        state = getArguments().getBoolean(ARG_BUTTON);
        mUserID =  getArguments().getLong(USER_ID_UUID);
        mTaskID = getArguments().getLong(ARG_UUID_KEY);
        mToDoList = new Task(mUserID);
        mToDoList = Repository.getInstance(getActivity()).getTask(mTaskID);
        mStringTitle = mToDoList.getMTitle();
        mStringDescription = mToDoList.getMDescription();
    }

    public static TaskFragment newInstance(Long task_id, boolean buttonAdd/*,UUID mUserUUID*/) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_UUID_KEY, task_id);
        args.putBoolean(ARG_BUTTON, buttonAdd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mTitle = view.findViewById(R.id.insert_title);
        mDescription = view.findViewById(R.id.insert_description);
        mDo = view.findViewById(R.id.done_condition);
        // mDatePicker = view.findViewById(R.mTaskID.date_input);
        mDateButton = view.findViewById(R.id.date_button);
        mSave = view.findViewById(R.id.save_to_list);
        mDelete = view.findViewById(R.id.delete_task);


        if (!state) {
            mDelete.setVisibility(View.GONE);
        } else {
            addToFragmentLayout();
        }

//setDateButton();
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d("onclick", "clicked");

                if (!mTitle.getText().toString().equals("")) {
                    //Toast.makeText(getActivity(),mTitle.getText().toString(),Toast.LENGTH_LONG).show();
                    mToDoList.setMTitle(mTitle.getText().toString());
                    mToDoList.setMDescription(mDescription.getText().toString());
                    mToDoList.setMDone(mDo.isChecked());
                    /*mToDoList = new Task();
                    Repository.getInstance().mAddTask(mToDoList);
                    mTaskID=mToDoList.getUUID();
                    mTitle.setText(null);
                    mDescription.setText(null);
                    mTitle.setHint(R.string.title_hint);
                    mDescription.setHint(R.string.description_hint);
                    mDo.setChecked(false);*/
                    mDelete.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "you cant left Title Empty!!", Toast.LENGTH_LONG).show();
                }

               /* Repository.getInstance().addToDo(mToDoList);
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
                DateFragment fragment = DateFragment.newInstance(Repository.getInstance(getActivity()).getTask(mTaskID).getMDate());
                fragment.setTargetFragment(TaskFragment.this, REQ_DATE_PICKER);
                fragment.show(getFragmentManager(), DATE_TAG);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckFragment fragment = CheckFragment.newInstance(mTaskID, mUserID);
                fragment.setTargetFragment(TaskFragment.this, REQ_DELETE_PICKER);
                fragment.show(getFragmentManager(), DELETE_TASK_TAG);

               /* Repository.getInstance().removeTask(mTaskID);
                if(Repository.getInstance().getProfileList().size()!=0) {
                    processOfDelete();
                }
                else{
                    mToDoList =new Task();
                    Repository.getInstance().mAddTask(mToDoList);
                    mTaskID=mToDoList.getUUID();
                    addToFragmentLayout(mToDoList);
                }*/
            }
        });


        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (mStringTitle == null || mStringTitle.equals("") && mToDoList.getMTitle() != null && mToDoList.getMTitle().equals("")) {
                    mStringTitle = mToDoList.getMTitle();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToDoList.setMTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mToDoList.getMTitle() == null || mToDoList.getMTitle() == "") {
                    mToDoList.setMTitle(mStringTitle);
                }

            }
        });
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mToDoList.setMDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;


    }

    private void setDateButton() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        String ouput = format.format(mToDoList.getMDate());
        mDateButton.setText(ouput);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0 | requestCode != 1) return;
        if (resultCode == Activity.RESULT_OK) {
            Date date = (Date) data.getSerializableExtra(DateFragment.EXTRA_DATE);
            mToDoList.setMDate(date);
            setDateButton();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String title = mToDoList.getMTitle();
        if (title == null || title.equals(""))
            title = " ";
        mToDoList.setMTitle(title);
        Repository.getInstance(getActivity()).update(mToDoList);
        mToDoList.setMDone(mDo.isChecked());
    }

    public void addToFragmentLayout() {
        mTitle.setText(mToDoList.getMTitle());
        mDescription.setText(mToDoList.getMDescription());
        mDo.setChecked(mToDoList.getMDone());
        mDateButton.setText(mToDoList.getMDate().toString());
    }

    public void processOfDelete(Long user_id) {

        Task toDoList_this = Repository.getInstance(getActivity()).getList(user_id).get(Repository.getInstance(getActivity()).getList(user_id).size() - 1);
        mTaskID = toDoList_this.getMTaskID();
        mToDoList = Repository.getInstance(getActivity()).getTask(mTaskID);
        addToFragmentLayout();

    }

   /* public void setId() {
        mTaskID = (UUID) getArguments().getSerializable(ARG_UUID_KEY);
        position = Repository.getInstance(getActivity()).getTask(mTaskID);
    }*/

    public void saveInitialValues() {
        mStringDescription = mToDoList.getMDescription();
        mStringTitle = mToDoList.getMTitle();
    }

   /* @Override
    public void onStop() {
        super.onStop();
        mToDoList.setDone(mDo.isChecked());

    }*/

    @Override
    public void onPause() {
        super.onPause();
        mToDoList.setMDone(mDo.isChecked());
        Repository.getInstance(getActivity()).update(mToDoList);
    }


}
