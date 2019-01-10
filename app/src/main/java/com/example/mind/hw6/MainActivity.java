package com.example.mind.hw6;

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
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mind.hw6.model.Repository;
import com.example.mind.hw6.model.Task;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String USER_ID = "userid";
    private PlaceholderFragment.RToDoAdapter mRToDoAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ImageView mImageView;
    private Task mToDoList;
    private UUID mUUIDuser;
    private static UUID UserUUID;


    public static Intent newIntent(Context context, UUID userid) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID, userid);
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
                mToDoList = new Task(mUUIDuser);
                //Repository.getInstance(getApplicationContext()).mAddToDo(mToDoList);
                UUID uuid = Repository.getInstance(getApplicationContext()).mAddToDo(mToDoList);
                // Toast.makeText(getApplicationContext(),uuid.toString(),Toast.LENGTH_LONG).show();
                Intent intent = TaskActivity.newIntent(MainActivity.this, uuid, false);
                startActivity(intent);
            }
        });

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

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

        private void updateUI() {
            mposition = getArguments().getInt(ARG_SECTION_NUMBER);
            List<Task> tasks = Repository.getInstance(getActivity()).getListForShow(mposition, UserUUID);
        /*    if (mposition!=1){
                mImageView.setVisibility(View.GONE);
            }*/
            if (mRToDoAdapter == null) {
                mRToDoAdapter = new RToDoAdapter(tasks);
                mRecyclerView.setAdapter(mRToDoAdapter);
            } else {
                mRToDoAdapter.setTask(tasks);
                mRToDoAdapter.notifyDataSetChanged();
            }
        }

        class RToDoViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextViewTitle;
            private TextView mTextViewIcon;
            private TextView mTextViewDate;
            private RelativeLayout mRelativeLayout;
            private Task mToDoList;

            public RToDoViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewTitle = itemView.findViewById(R.id.titleid);
                mTextViewIcon = itemView.findViewById(R.id.icon_text);
                mTextViewDate = itemView.findViewById(R.id.date_view_holder);
                mRelativeLayout = itemView.findViewById(R.id.viewholderid);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "ok made toast", Toast.LENGTH_SHORT).show();
                        Intent intent = TaskActivity.newIntent(getActivity(), mToDoList.getUUID(), true);

                        startActivity(intent);

                    }
                });
            }

            private void bind(Task toDoList) {
                mToDoList = toDoList;
                String firstChar;
                mTextViewTitle.setText(toDoList.getTitle());
                if (toDoList.getTitle() == null)
                    firstChar = "";
                else
                    firstChar = "" + toDoList.getTitle().charAt(0);
                mTextViewIcon.setText(firstChar.toUpperCase());
                mTextViewDate.setText(toDoList.getDate().toString());
                if (toDoList.isDone())
                    mRelativeLayout.setBackgroundColor(getContext().getColor(R.color.doneObjects));
            }
        }

        class RToDoAdapter extends RecyclerView.Adapter<RToDoViewHolder> {
            private List<Task> mTasks;
public RToDoAdapter(List<Task> tasks){
    mTasks =tasks;
}
            public void setTask(List<Task> tasks) {
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
                    Task toDoList = Repository.getInstance(getActivity()).getListForShow(getArguments()
                            .getInt(ARG_SECTION_NUMBER), UserUUID).get(position);
                    viewHolder.bind(toDoList);
                }
            }

            @Override
            public int getItemCount() {
                Log.d("bahman", "hello");
                mposition = getArguments().getInt(ARG_SECTION_NUMBER);
                int size  =Repository.getInstance(getActivity()).getListForShow(mposition, UserUUID).size();
                if (size> 0)
                    mImageView.setVisibility(View.GONE);
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
