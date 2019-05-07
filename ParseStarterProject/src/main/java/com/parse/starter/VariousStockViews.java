package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class VariousStockViews extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    EditText editText;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Button button;
    ListView lv;
    Toolbar toolbar;
    ImageButton imb;


    public void searchStock(View view){

        arrayList.clear();

        final ParseObject object = new ParseObject("Company");
        object.put("companyname",editText.getText().toString());
        object.put("username", ParseUser.getCurrentUser().getUsername());
        object.put("favourite","unchecked");
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    Toast.makeText(getApplicationContext(), "Getting Your Query", Toast.LENGTH_LONG).show();
                }
            }
        });

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Company");
        parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.orderByAscending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){
                    if(objects.size()>0){

                        for(int i=0;i<objects.size();i++){

                            arrayList.add(objects.get(i).getString("companyname"));

                        }
                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

        Intent intent = new Intent(VariousStockViews.this,SelectedStockItem.class);
        intent.putExtra("companyname", editText.getText().toString());
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_stock_views);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Stock Watch");

        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.lv);
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        button = (Button) findViewById(R.id.vsbtn);
        imb = (ImageButton) findViewById(R.id.imageButton4);
        imb.setOnClickListener(this);

        arrayList.clear();

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Company");
        parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.orderByAscending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){
                    if(objects.size()>0){

                        for(int i=0;i<objects.size();i++){

                            arrayList.add(objects.get(i).getString("companyname"));

                        }
                        arrayAdapter.notifyDataSetChanged();

                    }
                }

            }
        });



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

       Intent intent = new Intent(this, SelectedStockItem.class);
        intent.putExtra("companyname", arrayList.get(i));
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_various_stocks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if(item.getItemId()== R.id.logout)
        {
            ParseUser.logOut();

            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
        }else if(item.getItemId() == R.id.sortByName){



            arrayList.clear();

            ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Company");
            parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.orderByAscending("companyname");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){
                        if(objects.size()>0){

                            for(int i=0;i<objects.size();i++){

                                arrayList.add(objects.get(i).getString("companyname"));

                            }
                            arrayAdapter.notifyDataSetChanged();

                        }
                    }

                }
            });


        }else if(item.getItemId() == R.id.sortByRecent){


            arrayList.clear();

            ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Company");
            parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.orderByDescending("createdAt");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){
                        if(objects.size()>0){

                            for(int i=0;i<objects.size();i++){

                                arrayList.add(objects.get(i).getString("companyname"));

                            }
                            arrayAdapter.notifyDataSetChanged();

                        }
                    }

                }
            });



        }else if(item.getItemId() == R.id.favourites){

            editText.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
            listView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;



            arrayList.clear();

            ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Company");
            parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.whereEqualTo("favourite","checked");
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){
                        if(objects.size()>0){

                            for(int i=0;i<objects.size();i++){

                                arrayList.add(objects.get(i).getString("companyname"));

                            }
                            arrayAdapter.notifyDataSetChanged();

                        }
                    }

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nyse.com/listings_directory/stock"));
        startActivity(browserIntent);
    }
}
