package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SelectedStockItem extends AppCompatActivity implements View.OnClickListener {

    TextView company;
    String companyname;
    TextView showText;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ImageButton imageButton;
    ImageButton sendButton;
    EditText sendText;
    ProgressDialog progressDialog;


    String stockValue;
    String stockName;
    String currency;
    String bid;
    String bookValue;
    String lastTradeDate;
    String daysLow;
    String daysHigh;
    CheckBox checkBox;
    Boolean firstTime = true;
    Button fav;
    Toolbar toolbar;


    public void addToFav(View view) {


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Company");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("companyname", companyname);
        fav.setText("Added To Favourites");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    ParseObject object = objects.get(0);
                    object.put("favourite", "checked");
                    object.saveInBackground();
                }
            }
        });

    }


    public void refresh(View view) {
        Log.i("ref", "refresh");

        arrayList.clear();


        String query = "*";
        String symbol = companyname;
        String u = "http://query.yahooapis.com/v1/public/yql?q=select%20" + query + "%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22" + symbol + "%22%29&env=store://datatables.org/alltableswithkeys&format=json";
        String result = "";
        DownloadTask download = new DownloadTask();
        try {
            result = download.execute(u).get();

        } catch (Exception e) {
            e.printStackTrace();


        }

        try {
            JSONObject object1 = new JSONObject(result);
            String string1 = object1.getString("query");

            JSONObject object2 = new JSONObject(string1);
            String string2 = object2.getString("results");

            JSONObject object3 = new JSONObject(string2);
            String string3 = object3.getString("quote");

            JSONObject mainObject = new JSONObject(string3);

            String stockValue = mainObject.getString("Ask");
            String stockName = mainObject.getString("Name");
            company.setText(stockName);
            String currency = mainObject.getString("Currency");
            String bid = mainObject.getString("Bid");
            String bookValue = mainObject.getString("BookValue");
            String lastTradeDate = mainObject.getString("LastTradeDate");
            String daysLow = mainObject.getString("DaysLow");
            String daysHigh = mainObject.getString("DaysHigh");
            String open = mainObject.getString("Open");
            String daysRange = mainObject.getString("DaysRange");
            String volume = mainObject.getString("Volume");


            arrayList.add("Stock Value                            :  " + stockValue);
            arrayList.add("Stock Name                           :  " + stockName);
            arrayList.add("Currency                                :  " + currency);
            arrayList.add("Bid                                         :  " + bid);
            arrayList.add("Book Value                             :  " + bookValue);
            arrayList.add("Last Trade Date                     :  " + lastTradeDate);
            arrayList.add("Days Low                               :  " + daysLow);
            arrayList.add("DaysHigh                               :  " + daysHigh);
            arrayList.add("Open                                      :  " + open);
            arrayList.add("Days Range                           :  " + daysRange);
            arrayList.add("Volume                                  :  " + volume);


            listView.setAdapter(arrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        ParseObject msg = new ParseObject("Message");
        msg.put("message", sendText.getText().toString());
        msg.put("username", ParseUser.getCurrentUser().getUsername());
        ParseACL parseACL = new ParseACL();
        parseACL.setPublicReadAccess(true);
        msg.setACL(parseACL);
        msg.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Message has been posted :)", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                }
            }
        });

        sendText.setText("");

    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        URL url;
        String result = "";
        HttpURLConnection connection;


        @Override
        protected String doInBackground(String... strings) {

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1) {

                    char c = (char) data;
                    result += c;
                    data = reader.read();

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();


                return null;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SelectedStockItem.this, "Processing..",
                    "Please wait :(", true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.i("onpost", "executed");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_stock_item);
        Intent intent = getIntent();
        companyname = intent.getStringExtra("companyname");
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);


        company = (TextView) findViewById(R.id.textView3);
        listView = (ListView) findViewById(R.id.listView);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        fav = (Button) findViewById(R.id.btn);
        sendButton = (ImageButton) findViewById(R.id.imageButton3);
        sendText = (EditText) findViewById(R.id.editText7);
        sendButton.setOnClickListener(this);


        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("Company");
        q.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        q.whereEqualTo("companyname", companyname);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    ParseObject object = objects.get(0);
                    if (object.getString("favourite") != null && object.getString("favourite").equals("checked")) {
                        fav.setText("Added To Favorites");
                    }
                }
            }
        });


        String query = "*";
        String symbol = companyname;
        String u = "http://query.yahooapis.com/v1/public/yql?q=select%20" + query + "%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22" + symbol + "%22%29&env=store://datatables.org/alltableswithkeys&format=json";
        String result = "";
        DownloadTask download = new DownloadTask();
        try {
            result = download.execute(u).get();

        } catch (Exception e) {
            e.printStackTrace();


        }

        try {
            JSONObject object1 = new JSONObject(result);
            String string1 = object1.getString("query");

            JSONObject object2 = new JSONObject(string1);
            String string2 = object2.getString("results");

            JSONObject object3 = new JSONObject(string2);
            String string3 = object3.getString("quote");

            JSONObject mainObject = new JSONObject(string3);

            String stockValue = mainObject.getString("Ask");
            String stockName = mainObject.getString("Name");
            company.setText(stockName);
            String currency = mainObject.getString("Currency");
            String bid = mainObject.getString("Bid");
            String bookValue = mainObject.getString("BookValue");
            String lastTradeDate = mainObject.getString("LastTradeDate");
            String daysLow = mainObject.getString("DaysLow");
            String daysHigh = mainObject.getString("DaysHigh");
            String open = mainObject.getString("Open");
            String daysRange = mainObject.getString("DaysRange");
            String volume = mainObject.getString("Volume");


            arrayList.add("Stock Value                            :  " + stockValue);
            arrayList.add("Stock Name                           :  " + stockName);
            arrayList.add("Currency                                :  " + currency);
            arrayList.add("Bid                                         :  " + bid);
            arrayList.add("Book Value                             :  " + bookValue);
            arrayList.add("Last Trade Date                     :  " + lastTradeDate);
            arrayList.add("Days Low                               :  " + daysLow);
            arrayList.add("DaysHigh                               :  " + daysHigh);
            arrayList.add("Open                                      :  " + open);
            arrayList.add("Days Range                           :  " + daysRange);
            arrayList.add("Volume                                  :  " + volume);
            listView.setAdapter(arrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
