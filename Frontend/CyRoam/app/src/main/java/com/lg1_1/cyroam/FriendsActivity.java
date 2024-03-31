package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.lg1_1.cyroam.util.Friend;
import com.lg1_1.cyroam.util.FriendsListAdapter;

import java.util.ArrayList;

/**
 * has been implemented yet
 * @author Nicholas Kirschbaum
 */
public class FriendsActivity extends AppCompatActivity {

    private Button backButton2;
    private Button toAddFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        backButton2 = findViewById(R.id.homeButton);
        toAddFriends= findViewById(R.id.addButton);

        ListView mViewList = (ListView) findViewById(R.id.listvire);

        ArrayList<Friend> list = new ArrayList<>();
        Friend one = new Friend("John", 32, 23);
        Friend two = new Friend("steve", 32, 23);
        list.add(one);
        list.add(two);

        //get list of friends here
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(this, R.layout.format_listview, list);
        mViewList.setAdapter((ListAdapter) friendsListAdapter);



        //backButton2.findViewById(R.id.);
        //toAddFriends.findViewById(R.id.AddButton);
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, PortalScreenActivity.class);
                startActivity(intent);
            }
        });

        toAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, AddFriends.class);
                startActivity(intent);
            }
        });


    }
}