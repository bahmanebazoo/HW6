package com.example.mind.hw6;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mind.hw6.model.ListLab;
import com.example.mind.hw6.model.ToDoList;

public class MainActivity extends AppCompatActivity {
    private PlaceholderFragment.RToDoAdapter mRToDoAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddToDoActivity.newIntent(MainActivity.this, 1));
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
            updateUI();
            //Intent intent=getActivity().getIntent();

            return rootView;
        }

        private  void updateUI() {
            if (mRToDoAdapter == null) {
                mRToDoAdapter = new RToDoAdapter();
                mRecyclerView.setAdapter(mRToDoAdapter);
            } else {
                mRToDoAdapter.notifyDataSetChanged();
            }
        }

        class RToDoViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextViewTitle;
            private TextView mTextViewicon;

            public RToDoViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewTitle = itemView.findViewById(R.id.titleid);
                mTextViewicon = itemView.findViewById(R.id.icon_text);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "ok made toast", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void bind(String title) {

                mTextViewTitle.setText(title);
                String firstChar = "" + title.charAt(0);
                mTextViewicon.setText(firstChar.toUpperCase());
            }
        }

        class RToDoAdapter extends RecyclerView.Adapter<RToDoViewHolder> {

            @NonNull
            @Override
            public RToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_view_holder, viewGroup, false);

                return new RToDoViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RToDoViewHolder viewHolder, int position) {

                ToDoList toDoList = ListLab.getInstance().getListForShow(getArguments()
                        .getInt(ARG_SECTION_NUMBER)).get(position);
                viewHolder.bind(toDoList.getTitle());
            }

            @Override
            public int getItemCount() {
                Log.d("bahman", "hello");

                return ListLab.getInstance().getListForShow(getArguments().getInt(ARG_SECTION_NUMBER)).size();
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
    protected void onResume() {
        super.onResume();

    }
}
