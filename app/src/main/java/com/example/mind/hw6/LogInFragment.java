package com.example.mind.hw6;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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
import com.example.mind.hw6.model.ProfileDao;
import com.example.mind.hw6.database.Repository;
import com.example.mind.hw6.model.TaskDao;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    ProfileDao mProfileDao;
    TaskDao mTaskDao;


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
                    Profile profile = new Profile();
                    Intent intent = MainActivity.newIntent(getActivity(), profile.getMUserID());
                    Toast.makeText(getActivity(), getString(R.string.guest_openning_alert), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                if (mCountClick == 2) {
                  //  Toast.makeText(getActivity(), ""+Build.VERSION.BASE_OS, Toast.LENGTH_SHORT).show();

                    StringBuilder builder = new StringBuilder();
                    builder.append("android : ").append(Build.VERSION.RELEASE);

                    Field[] fields = Build.VERSION_CODES.class.getFields();
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        int fieldValue = -1;

                        try {
                            fieldValue = field.getInt(new Object());
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        if (fieldValue == Build.VERSION.SDK_INT) {
                            builder.append(" : ").append(fieldName).append(" : ");
                            builder.append("sdk=").append(fieldValue);
                        }
                        Toast.makeText(getActivity(), "OS: " + builder.toString(), Toast.LENGTH_SHORT).show();
                    }


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
                        mProfile =  Repository.getInstance(getActivity()).getProfileByEmail(mEmail.getText().toString());
                    }
                if (mProfile == null) {
                    if (mEmail.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), " enter your email please ", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), " this Email didn't signed up\n    please first sign up", Toast.LENGTH_LONG).show();
                } else if (mProfile.getMPasssword().equals(mPassword.getText().toString())) {
                    Intent intent = MainActivity.newIntent(getActivity(), mProfile.getMUserID());
                    startActivity(intent);
                } else
                    Toast.makeText(getActivity(), " wrong password.  do you forget?", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
