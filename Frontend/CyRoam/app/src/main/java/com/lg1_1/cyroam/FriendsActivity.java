package com.lg1_1.cyroam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @deprecated has not been implemented yet
 * @author Nicholas Kirschbaum
 */
public class FriendsActivity extends AppCompatActivity {

    Button backButton;
    Button toAddFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ListView mViewList = (ListView) findViewById(R.id.listvire);

        ArrayList<String> list = new ArrayList<>();
        //get list of friends here
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(this, R.id.format_list_view);
        backButton.findViewById(R.id.HomeButton);
        toAddFriends.findViewById(R.id.AddButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, PortalScreenActivity.class);
                startActivity(intent);
            }
        });toAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsActivity.this, AddFriends.class);
                startActivity(intent);
            }
        });
    }
}