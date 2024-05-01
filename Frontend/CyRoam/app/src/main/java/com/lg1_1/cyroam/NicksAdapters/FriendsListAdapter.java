package com.lg1_1.cyroam.NicksAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lg1_1.cyroam.ProfileActivity;
import com.lg1_1.cyroam.R;
import com.lg1_1.cyroam.objects.Friend;

import java.util.ArrayList;



public class FriendsListAdapter extends ArrayAdapter<Friend> {

    private Context context1;
    int stuff;
    public FriendsListAdapter(Context context, int formatListview, ArrayList<Friend> list) {
        super(context, formatListview, list);
        context1 = context;
        stuff = formatListview;
    }


    public View getView(int position, View convert, ViewGroup parent) {

        // Get the current Friend object
        Friend friend = getItem(position);

        if (convert == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context1);
            convert = layoutInflater.inflate(stuff, parent, false);
        }

        TextView user = (TextView) convert.findViewById(R.id.textView1);
        TextView score = (TextView) convert.findViewById(R.id.textView2);
        TextView Id = (TextView) convert.findViewById(R.id.textView3);

        // Set data to the views
        user.setText("Friend: " + friend.getName());
        score.setText("Score: " + friend.getTheirScore());
        Id.setText("Id: " + friend.getTheirId());

        // Set OnClickListener to the convert view or any specific view inside it
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event here
                // For example, you might want to open a new activity with details of the clicked friend
                String idstring = Integer.toString(friend.getTheirId());
                Intent intent = new Intent(context1, ProfileActivity.class);
                intent.putExtra("friendId", idstring);
                intent.putExtra("friendName", friend.getName());
                context1.startActivity(intent);
            }
        });

        return convert;
    }
}
