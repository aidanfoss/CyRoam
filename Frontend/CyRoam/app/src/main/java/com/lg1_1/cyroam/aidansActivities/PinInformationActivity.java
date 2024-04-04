package com.lg1_1.cyroam.aidansActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lg1_1.cyroam.LeaderBoard;
import com.lg1_1.cyroam.MapsActivity;
import com.lg1_1.cyroam.PortalScreenActivity;
import com.lg1_1.cyroam.R;
import com.lg1_1.cyroam.util.Pin;
import com.lg1_1.cyroam.volley.pinVolley;
import com.lg1_1.cyroam.websockets.CommentListener;
import com.lg1_1.cyroam.websockets.WebSocketManager;
import com.lg1_1.cyroam.websockets.commentsWebSocket;

import org.w3c.dom.Text;

import java.net.URISyntaxException;

public class PinInformationActivity extends AppCompatActivity implements CommentListener {

    private final String TAG = "PinInfoActivity";
    private TextView pinInfo;
    private TextView commentView;
    private Button deleteButton;
    private Button sendButton;
    private EditText commentSendBox;
    private EditText deleteEditBox;
    private int passedPinId;
    private pinVolley pinVolley;
    boolean adminPerms;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_info);

        //grab user information


        //define UI
        commentView = findViewById(R.id.pinCommentText);
        pinInfo = findViewById(R.id.pinInfoText);
        sendButton = findViewById(R.id.pinInfoSendComment);
        commentSendBox = findViewById(R.id.pinInfoCommentEditText);
        deleteButton = findViewById(R.id.pinInfoDelComButton);
        deleteEditBox = findViewById(R.id.pinInfoIDDeleteTextBox);

        deleteButton.setVisibility(View.INVISIBLE);
        deleteEditBox.setVisibility(View.INVISIBLE);

        this.pinVolley = new pinVolley(this);

        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                passedPinId = extras.getInt("ID");
                adminPerms = true;

                if (adminPerms){
                    deleteButton.setVisibility(View.VISIBLE);
                    deleteEditBox.setVisibility(View.VISIBLE);
                }

                pinVolley.fetchPinData(passedPinId, new pinVolley.FetchPinCallback() {
                    @Override
                    public void onSuccess(Pin pin) {
                        Log.w(TAG, "Fetch Pin in PinInfoActivity Success: " + pin.getDebugDescription());
                        pinInfo.append("Name: " + pin.getName() + "\n");
                        pinInfo.append("Description: " + pin.getDescription() + "\n");
                        pinInfo.append("Latitude: " +String.valueOf(pin.getLat()) + "\n");
                        pinInfo.append("Longitude: " + String.valueOf(pin.getLong()) + "\n");
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Log.w(TAG, "Fetch Pin in PinInfoActivity Failed: " + errorMessage);
                    }
                });
            } else {Log.w(TAG, "NULL EXTRAS");}
            WebSocketManager.getInstance().openCommentConnection(passedPinId, this);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentSendBox.getText() != null){
                    String comment = String.valueOf(commentSendBox.getText());
                    WebSocketManager.getInstance().sendComment(comment);
                    commentView.append(comment + "\n");
                }
            }
        });
    }

    /**
     * function that sees when a comment is received and displays it
     * @author Aidan Foss
     * @param comment inputs the full string to be displayed
     */
    @Override
    public void onCommentReceived(String comment) {
        Log.v(TAG,"Comment Received in PinInfoActivity");
        commentView.append(comment + "\n");
    }
    //activity that displays more info on the pin clicked, and has a comments box.

}
