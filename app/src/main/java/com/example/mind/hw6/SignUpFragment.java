package com.example.mind.hw6;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mind.hw6.model.Profile;
import com.example.mind.hw6.model.Repository;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    private Profile mProfile;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirm;
    private Button mButton;
    private boolean mBooleanEmail = false;
    private boolean mBooleanPassword = false;
    private boolean mBooleanConfirm = false;


    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mEmail = view.findViewById(R.id.sign_up_email_input);
        mPassword = view.findViewById(R.id.signup_password);
        mConfirm = view.findViewById(R.id.sign_up_confirm_password_input);
        mButton = view.findViewById(R.id.sign_up_ok);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                boolean add = true;
               /* for (int i = 0; i < ProfileLab.getInstance(getActivity()).getProfileList().size(); i++) {
                    if (ProfileLab.getInstance(getActivity()).getProfileByEmail(mEmail.getText().toString())!=null) {
                        check++;
                        add = false;

                    }
                }*/
                if (Repository.getInstance(getActivity()).getProfileByEmail(mEmail.getText().toString())!=null) {
                    Toast.makeText(getActivity(), getString(R.string.dublicate_email), Toast.LENGTH_LONG).show();
                } else
                    mBooleanEmail = true;
                if (mBooleanEmail && mPassword.getText().toString().length() > 5 && mPassword.getText().toString().length() < 13)
                    mBooleanPassword = true;
                else
                    mBooleanPassword = false;

                if (mBooleanPassword & mPassword.getText().toString().equals(mConfirm.getText().toString()))
                    mBooleanConfirm = true;
                else
                    mBooleanConfirm = false;

                if (mBooleanConfirm && mBooleanPassword && mBooleanEmail) {
                    //set to database
                    mEmail.setBackgroundColor(getActivity().getColor(R.color.White));
                    mConfirm.setBackgroundColor(getActivity().getColor(R.color.White));
                    mPassword.setBackgroundColor(getActivity().getColor(R.color.White));
                    mProfile = new Profile();
                    mProfile.setDate(new Date(System.currentTimeMillis()));
                    mProfile.setEmail(mEmail.getText().toString());
                    mProfile.setPasssword(mPassword.getText().toString());
                    Repository.getInstance(getActivity()).addProfile(getActivity(), mProfile);
                    Toast.makeText(getActivity(), getString(R.string.approve_signup), Toast.LENGTH_SHORT).show();
                    Intent intent = LogInActivity.newIntent(getActivity());
                    startActivity(intent);

                } else if (!mBooleanEmail) {
                    Toast.makeText(getActivity(), getString(R.string.wrong_email), Toast.LENGTH_SHORT).show();
                    mEmail.setBackgroundColor(getActivity().getColor(R.color.Alarm));
                } else if (!mBooleanPassword) {
                    Toast.makeText(getActivity(), getString(R.string.pass_limits), Toast.LENGTH_SHORT).show();
                    mPassword.setBackgroundColor(getActivity().getColor(R.color.Alarm));
                } else if (!mBooleanConfirm) {
                    Toast.makeText(getActivity(), getString(R.string.confirmation), Toast.LENGTH_SHORT).show();
                    mConfirm.setBackgroundColor(getActivity().getColor(R.color.Alarm));
                    mConfirm.clearComposingText();
                }
                mBooleanConfirm = mBooleanEmail = mBooleanPassword = false;
            }

        });

        return view;
    }

}
