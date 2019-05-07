package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView = (TextView) findViewById(R.id.mailtv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","kandukuriadityasai@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "INFORMATION ABOUT THE RELATING PROBLEM");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "type here....");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });


    }
}
