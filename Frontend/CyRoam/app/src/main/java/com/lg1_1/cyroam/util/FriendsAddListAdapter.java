package com.lg1_1.cyroam.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lg1_1.cyroam.R;

import java.util.ArrayList;

public class FriendsAddListAdapter extends ArrayAdapter<Friend> {
    private Context context1;
    int stuff;
    public FriendsAddListAdapter(Context context, int formatListview, ArrayList<Friend> list) {
        super(context, formatListview, list);
        context1 = context;
        stuff = formatListview;
    }

    public View getView(int position, View convert, ViewGroup parent){




        String name = getItem(position).getName();
        int theirId = getItem(position).getTheirId();
        int theirScore = getItem(position).getTheirScore();

        Friend friend = new Friend(name, theirScore, theirId);

        LayoutInflater layoutInflater = LayoutInflater.from(context1);
        convert = layoutInflater.inflate(stuff, parent,false);

        TextView user = (TextView) convert.findViewById(R.id.textView17);
        Button btn = convert.findViewById(R.id.buttonProblem);
        btn.setFocusable(false);
        btn.setClickable(false);
        //String str1 = Integer.toString(score);
        //String str2 = Integer.toString(Id);
        user.setText("Friend: "+name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here
                user.setText("<3");
            }
        });


        return convert;

    }
}
