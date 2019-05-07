package com.parse.starter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by adity on 14-07-2016.
 */
public class AddDialog extends DialogFragment implements View.OnClickListener {
    TextView tv;
    EditText et;
    Button send;
    ImageButton imgButton;
    public AddDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et = (EditText) view.findViewById(R.id.editText);
        send = (Button) view.findViewById(R.id.button_send);
        send.setOnClickListener(this);
        imgButton = (ImageButton) view.findViewById(R.id.imageButton2);
        imgButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.button_send){

                ParseObject msg = new ParseObject("Message");
                msg.put("message", et.getText().toString());
                msg.put("username", ParseUser.getCurrentUser().getUsername());
                ParseACL parseACL = new ParseACL();
                parseACL.setPublicReadAccess(true);
                msg.setACL(parseACL);
                msg.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getActivity(), "Message has been posted", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }else if(v.getId() == R.id.imageButton2){
                dismiss();
        }
    }

    }
