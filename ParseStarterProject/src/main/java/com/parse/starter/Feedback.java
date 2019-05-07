package com.parse.starter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Feedback extends DialogFragment implements View.OnClickListener{

    TextView tv;
    EditText et;
    DatePicker dp;
    Button button;
    ImageButton imgbutton;

    public Feedback() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_feedback, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.textView);
        et = (EditText) view.findViewById(R.id.editText);
        dp = (DatePicker) view.findViewById(R.id.datePicker);
        button = (Button) view.findViewById(R.id.button_toAddListToDatabase);
        button.setOnClickListener(this);
        imgbutton = (ImageButton) view.findViewById(R.id.imgbt);
        imgbutton.setOnClickListener(this);


    }

    public void addToDatabase(){

        ParseObject parseObject = new ParseObject("Feedback");
        parseObject.add("username", ParseUser.getCurrentUser().getUsername());
        parseObject.add("feedback", et.getText().toString());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText( getActivity()," Feedback sent :D " , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Error in sending feedback... Try after some time", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_toAddListToDatabase){
            addToDatabase();
        }else if(view.getId() == R.id.imgbt){
            dismiss();
        }
    }
}