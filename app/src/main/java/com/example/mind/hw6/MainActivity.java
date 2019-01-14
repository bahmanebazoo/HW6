package com.example.mind.hw6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mind.hw6.model.Repository;
import com.example.mind.hw6.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String USER_ID = "userid";
    public static final String TASK_ID = "taskid";
    public static final String DELETE_ALL_TAG = "delete_all";
    public static final int REQ_SHOW_TASK_TAG = 0;
    public static final int REQ_EDIT_TASK_TAG = 1;
    public static final String EDIT_TASK_SERIALIZABLE = "task_effected";
    public static final String TASK_TAG = "show_task";
    public static final String EDIT_TASK_TAG = "edit_tag";
    private PlaceholderFragment.RToDoAdapter mRToDoAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ImageView mImageView;
    private Task mTask;
    private UUID mUUIDuser;
    private static UUID UserUUID;


    public static Intent newIntent(Context context, UUID userid) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID, userid);
        return intent;

    }

   /* public static Intent newIntent(Context context, Serializable[] date_time_saveOrDelete) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EDIT_TASK_SERIALIZABLE, date_time_saveOrDelete);
        return intent;

    }*/

    public static Intent newIntent(Context context, UUID task_id, boolean nothing) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID, task_id);
        return intent;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUUIDuser = (UUID) getIntent().getSerializableExtra(USER_ID);
        UserUUID = mUUIDuser;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mImageView = findViewById(R.id.imagempty);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask = new Task(mUUIDuser);
                //Repository.getInstance(getApplicationContext()).mAddTask(mTask);
                UUID uuid = Repository.getInstance(getApplicationContext()).mAddTask(mTask);
                // Toast.makeText(getApplicationContext(),uuid.toString(),Toast.LENGTH_LONG).show();
                Intent intent = TaskActivity.newIntent(MainActivity.this, uuid, false);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("resume", "bahman");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CheckFragment fragment = CheckFragment.newInstance(null, mUUIDuser);
        fragment.show(getSupportFragmentManager(), DELETE_ALL_TAG);
        // Repository.getInstance(getApplicationContext()).removeTasks(mUUIDuser);

        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private EditText mTitleid;
        private EditText micon_text;

        private RecyclerView mRecyclerView;
        private RToDoAdapter mRToDoAdapter;
        private String mtitle;
        private int mposition;
        private ImageView mImageView;



        @Override
        public void onResume() {
            super.onResume();
            updateUI();
        }

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
           /* TextView textView = rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
            mRecyclerView = rootView.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mImageView = rootView.findViewById(R.id.imagempty);
            updateUI();
            //Intent intent=getActivity().getIntent();

            return rootView;
        }

        public void updateUI() {
            mposition = getArguments().getInt(ARG_SECTION_NUMBER);
            List<Task> tasks = Repository.getInstance(getActivity()).getListForShow(mposition, UserUUID);
            if (tasks.size()>0)
                mImageView.setVisibility(View.GONE);
            else
                mImageView.setVisibility(View.VISIBLE);

            if (mRToDoAdapter == null) {
                mRToDoAdapter = new RToDoAdapter(tasks);
                mRecyclerView.setAdapter(mRToDoAdapter);
            } else {
                mRToDoAdapter.setList(tasks);
                mRToDoAdapter.notifyDataSetChanged();
            }
        }

        class RToDoViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextViewTitle;
            private TextView mTextViewIcon;
            private TextView mTextViewDate;
            private RelativeLayout mRelativeLayout;
            private Task mTask;

            public RToDoViewHolder(@NonNull final View itemView) {
                super(itemView);

                mTextViewTitle = itemView.findViewById(R.id.titleid);
                mTextViewIcon = itemView.findViewById(R.id.icon_text);
                mTextViewDate = itemView.findViewById(R.id.date_view_holder);
                mRelativeLayout = itemView.findViewById(R.id.viewholderid);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getActivity(),"ling click",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Toast.makeText(getActivity(), "ok made toast", Toast.LENGTH_SHORT).show();
                        Intent intent = TaskActivity.newIntent(getActivity(), mTask.getUUID(), true);

                        startActivity(intent);*/

                        UUID id = mTask.getUUID();
                        ShowTaskFragment showTaskFragment = ShowTaskFragment.newInstance(id);
                        showTaskFragment.setTargetFragment(PlaceholderFragment.this, REQ_SHOW_TASK_TAG);
                        showTaskFragment.show(getFragmentManager(), TASK_TAG);
                    }
                });
            }

            private String getFormattedDate(String s) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(s);
                return dateFormat.format(Date.parse(mTask.getDate().toString()));
            }


            private void bind(Task task) {
                mTask = task;
                String firstChar;
                mTextViewTitle.setText(task.getTitle());
                if (task.getTitle() == null)
                    firstChar = "";
                else
                    firstChar = " " + task.getTitle().charAt(0);
                mTextViewIcon.setText(firstChar.toUpperCase());
                mTextViewDate.setText(getFormattedDate("dd-mmm-yyyy  hh:mm a"));
                if (task.isDone())
                    mRelativeLayout.setBackgroundColor(getContext().getColor(R.color.doneObjects));
            }

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            /*if(requestCode!=Activity.RESULT_OK)
                return;*/
            if (resultCode != Activity.RESULT_OK)
                return;
            if (requestCode == REQ_SHOW_TASK_TAG) {
                Toast.makeText(getActivity(), "new dialog oppened", Toast.LENGTH_SHORT).show();
                UUID uuid = (UUID) data.getSerializableExtra(ShowTaskFragment.EXTRA_TASK_ID);
                EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(uuid);
                editTaskFragment.setTargetFragment(PlaceholderFragment.this, REQ_EDIT_TASK_TAG);
                editTaskFragment.show(getFragmentManager(),EDIT_TASK_TAG);
                //HERE MUST GO TO DIALOG-FRAGMENT FOR EDITING
            }
            if(requestCode==REQ_EDIT_TASK_TAG){
                updateUI();
            }
        }

        class RToDoAdapter extends RecyclerView.Adapter<RToDoViewHolder> {
            private List<Task> mTasks;

            public RToDoAdapter(List<Task> tasks) {
                mTasks = tasks;
            }

            public void setList(List<Task> tasks) {
                mTasks = tasks;
            }

            @NonNull
            @Override
            public RToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_view_holder, viewGroup, false);

                return new RToDoViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RToDoViewHolder viewHolder, int position) {
                if (getArguments() != null) {
                    Task task = mTasks.get(position);
                    viewHolder.bind(task);
                }
            }

            @Override
            public int getItemCount() {
                Log.d("bahman", "hello");
                mposition = getArguments().getInt(ARG_SECTION_NUMBER);
                int size = Repository.getInstance(getActivity()).getListForShow(mposition, UserUUID).size();
                if (size > 0)
                    mImageView.setVisibility(View.GONE);
                else
                    mImageView.setVisibility(View.VISIBLE);
                return size;
            }
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //here must all of guest Tasks remove from Repository
    }


}
