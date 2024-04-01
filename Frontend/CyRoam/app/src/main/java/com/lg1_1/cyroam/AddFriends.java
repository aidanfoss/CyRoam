package com.lg1_1.cyroam;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.RequestQueue;
import com.lg1_1.cyroam.util.Friend;
import com.lg1_1.cyroam.util.FriendsAddListAdapter;

import java.util.ArrayList;

/**
 * This screen holds invites from other people
 * as well as allows you to send invites
 * Add Friends is a screen that is only accessable via friends activity
 */
public class AddFriends extends AppCompatActivity {


    /**
     * @author Nicholas Kirschbaum
     * tag for debugging
     */
    private String TAG = "ADDFriendActivity";
    /**
     * @author Nicholas Kirschbaum
     * Takes url from Main
     */
    private String mainURL = MainActivity.url;
    /**
     * @author Nicholas Kirschbaum
     * Is a button that sends invite to the user that is typed
     */
    private Button friendSearch;

    private Button bybyButton;
    private RequestQueue queue;
    /**
     * @author Nicholas Kirschbaum
     * prints out screen with button on it(more in the works)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        ConstraintLayout constraintLayout = findViewById(R.id.main2);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1", "Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }*/
        friendSearch = findViewById(R.id.searchbutton);
        bybyButton = findViewById(R.id.addBackButton);

        ListView mViewList = (ListView) findViewById(R.id.listView2);
        ArrayList<Friend> list = new ArrayList<>();
        Friend one = new Friend("John", 32, 23);
        Friend two = new Friend("steve", 32, 23);
        list.add(one);
        list.add(two);

        //get list of friends here
        FriendsAddListAdapter friendsListAdapter = new FriendsAddListAdapter(this, R.layout.format2_listview, list);
        mViewList.setAdapter((ListAdapter) friendsListAdapter);



        friendSearch.setOnClickListener(view -> {
                    createnotif();
        });
        bybyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFriends.this, PortalScreenActivity.class);
                startActivity(intent);
            }


        });
        /*
        mViewList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<Friend> parent, View view, int position, long l) {
                String curUsername = "bossf";
                String Newfriend = parent.toString();
                addfriends(curUsername, Newfriend);
            }
            // handle click here
        });
        */

    }

    /**
     * @author Nicholas Kirschbaum
     * When called it created a notification
     */
    private void createnotif() {
        String id = "Whatch this work";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(id);
            if (channel == null) {
                channel = new NotificationChannel(id, "Channel line", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("[Channel Discription]");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 1000, 200, 340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }


        }
        Intent notifIntent = new Intent(this, NotificationActivity.class);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notifIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, notifIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.iowa_state_clipart_4_removebg_preview)
                .setContentTitle("Sent!!!")
                .setContentText("Your Friend Request has been sent")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{100, 1000, 200, 340})
                .setAutoCancel(false)
                .setTicker("Notification");
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
           // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        m.notify(1, builder.build());

    }

    private void simpleCreatenotif() {
        final String id = "Whatch_this_work";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.iowa_state_clipart_4_removebg_preview);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(1, builder.build());


    }


}