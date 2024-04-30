package com.lg1_1.cyroam.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lg1_1.cyroam.AddFriends;
import com.lg1_1.cyroam.MainActivity;
import com.lg1_1.cyroam.objects.User;
/**
 * Enables user to add friends and receive notification about friend request.
 * @author Nicholas Kirschbaum
 */
public class friendVolley {
    private static final String TAG = "PinRequest";
    private static final String BASE_URL = MainActivity.url;
    private AddFriends addFriends;
    private RequestQueue queue;
    private Context context;


    /**
     *
     * @param context the class that's running it, (when constructed, always use "this")
     */
    public friendVolley(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context); //tutorial said to do this, but not sure why. couldn't it just be (this) for context?
    }






    /**
     * adds friend to the list of friends they have
     * @author Nicholas Kirschbaum
     */

    public void addfriendsReq(String curUsername, User Newfriend, final addFriendCallback callback){
        Log.v("Nick Websocket", "message made to addfriendsReq" + curUsername);
        addFriends.createnotif(curUsername);
    }




    /**
     * adds friend callback
     * @author Nicholas Kirschbaum
     */
    public interface addFriendCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }




}

