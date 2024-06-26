package com.lg1_1.cyroam.NicksAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lg1_1.cyroam.R;
import com.lg1_1.cyroam.objects.Friend;

import java.util.ArrayList;

public class LeaderBoardListAdapter extends ArrayAdapter<Friend> {

    private Context context1;
    int stuff;
    public LeaderBoardListAdapter(Context context, int formatListview, ArrayList<Friend> list) {
        super(context, formatListview, list);
        context1 = context;
        stuff = formatListview;
    }

    public View getView(int position, View convert, ViewGroup parent){

        String name = getItem(position).getName();
        //this code doesnt get there Id
        int theirId = 0;
        int theirScore = getItem(position).getTheirScore();

        Friend friend = new Friend(name, theirScore, theirId);

        LayoutInflater layoutInflater = LayoutInflater.from(context1);
        convert = layoutInflater.inflate(stuff, parent,false);

        TextView user = (TextView) convert.findViewById(R.id.format3textView1);
        TextView score = (TextView) convert.findViewById(R.id.format3textView2);
        //TextView Id = (TextView) convert.findViewById(R.id.textView3);
        //String str1 = Integer.toString(score);
        //String str2 = Integer.toString(Id);
        user.setText("User: "+name);
        score.setText("Score " + theirScore);
        //Id.setText("Id: " + theirId);


        return convert;

    }
}
