package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ListUsers extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        setTitle("Users's List");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ListUsers.this, ShowUsersPosts.class);
                intent.putExtra("user", arrayList.get(i));
                startActivity(intent);

            }
        });



        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e==null){

                    for(int i=0;i<objects.size();i++){

                        arrayList.add(objects.get(i).getUsername());

                    }

                    arrayAdapter.notifyDataSetChanged();

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_userslist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId()== R.id.share) {

        AddDialog newDialog = new AddDialog();
        newDialog.show(getSupportFragmentManager(), "fragment");
        }else if(item.getItemId() == R.id.sortByNameAsc) {

            arrayList.clear();
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.orderByAscending("username");

            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if(e==null){

                        for(int i=0;i<objects.size();i++){

                            arrayList.add(objects.get(i).getUsername());

                        }

                        arrayAdapter.notifyDataSetChanged();

                    }

                }
            });


        } else if(item.getItemId()== R.id.logout)
            {
                ParseUser.logOut();

                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }else if(item.getItemId() == R.id.sortByNameDes){


            arrayList.clear();
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

            parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            parseQuery.orderByDescending("username");

            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if(e==null){

                        for(int i=0;i<objects.size();i++){

                            arrayList.add(objects.get(i).getUsername());

                        }

                        arrayAdapter.notifyDataSetChanged();

                    }

                }
            });


        }
        return super.onOptionsItemSelected(item);
        }

}

