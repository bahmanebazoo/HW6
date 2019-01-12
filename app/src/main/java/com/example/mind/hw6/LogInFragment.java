package com.example.mind.hw6;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mind.hw6.model.Profile;
import com.example.mind.hw6.model.Repository;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    private Profile mProfile;
    private EditText mEmail;
    private EditText mPassword;
    private ImageView mVisiblePassword;
    private ImageView mImage;
    private TextView mSignUp;
    private TextView mForget;
    private Button mLogIn;
    private int mCountClick;
    private long mTime1;
    private long mTime2;


    public static LogInFragment newInstance() {

        Bundle args = new Bundle();

        LogInFragment fragment = new LogInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCountClick = 0;

    }

    @Override
    public void onResume() {
        super.onResume();
        mCountClick = 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mEmail = view.findViewById(R.id.email_input);
        mPassword = view.findViewById(R.id.password_input);
        mVisiblePassword = view.findViewById(R.id.visiblepassword);
        mImage = view.findViewById(R.id.logo);
        mSignUp = view.findViewById(R.id.signup);
        mForget = view.findViewById(R.id.forgetpassword);
        mLogIn = view.findViewById(R.id.log_in_button);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCountClick == 0) {
                    mTime1 = System.currentTimeMillis();
                }
                mCountClick++;
                if (mCountClick == 5) {
                    mCountClick = 0;
                    mTime2 = System.currentTimeMillis();
                }
                if (mCountClick == 0 && mTime2 - mTime1 <= 1000) {
                    Intent intent = MainActivity.newIntent(getActivity(), UUID.randomUUID());
                    Toast.makeText(getActivity(), getString(R.string.guest_openning_alert), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

        mVisiblePassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (Repository.getInstance(getActivity()).getProfileByEmail(mEmail.getText().toString())!=null) {
                        mProfile = Repository.getInstance(getActivity()).getProfileByEmail(mEmail.getText().toString());
                    }
                if (mProfile == null) {
                    if (mEmail.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), " enter your email please ", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), " this Email didn't signed up\n    please first sign up", Toast.LENGTH_LONG).show();
                } else if (mProfile.getPasssword().equals(mPassword.getText().toString())) {
                    Intent intent = MainActivity.newIntent(getActivity(), mProfile.getUUID());
                    startActivity(intent);
                } else
                    Toast.makeText(getActivity(), " wrong password.  do you forget?", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
