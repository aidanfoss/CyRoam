package com.lg1_1.cyroam.aidansActivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lg1_1.cyroam.Managers.LoginManager;
import com.lg1_1.cyroam.Managers.WebSocketManager;
import com.lg1_1.cyroam.R;
import com.lg1_1.cyroam.objects.Pin;
import com.lg1_1.cyroam.volley.pinVolley;
import com.lg1_1.cyroam.websockets.CommentListener;

import java.net.URISyntaxException;

public class PinInformationActivity extends AppCompatActivity implements CommentListener {

    private final String TAG = "PinInfoActivity";
    private TextView pinInfo;
    private TextView commentView;
    private EditText commentSendBox;
    private EditText deleteEditBox;
    private int passedPinId;
    boolean adminPerms;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_info);

        //grab user information


        //define UI
        ImageView imageView = findViewById(R.id.imageView3);
        Button deletePinButton = findViewById(R.id.pinInfoDelComButton2);
        commentView = findViewById(R.id.pinCommentText);
        pinInfo = findViewById(R.id.pinInfoText);
        Button sendButton = findViewById(R.id.pinInfoSendComment);
        commentSendBox = findViewById(R.id.pinInfoCommentEditText);
        Button deleteButton = findViewById(R.id.pinInfoDelComButton);
        deleteEditBox = findViewById(R.id.pinInfoIDDeleteTextBox);

        deleteButton.setVisibility(View.INVISIBLE);
        deleteEditBox.setVisibility(View.INVISIBLE);

        com.lg1_1.cyroam.volley.pinVolley pinVolley = new pinVolley(this);

        Bundle extras = getIntent().getExtras();
        try {
            if (extras != null) {
                passedPinId = extras.getInt("ID");
                adminPerms = LoginManager.getInstance().getUser().getPermission() == 2;

                if (adminPerms){
                    deleteButton.setVisibility(View.VISIBLE);
                    deleteEditBox.setVisibility(View.VISIBLE);
                    deletePinButton.setVisibility(View.VISIBLE);
                }

                pinVolley.fetchPinData(passedPinId, new pinVolley.FetchPinCallback() {
                    @Override
                    public void onSuccess(Pin pin) {
                        Log.w(TAG, "Fetch Pin in PinInfoActivity Success: " + pin.getDebugDescription());
                        pinInfo.append("Name: " + pin.getName() + "\n");
                        if (!(pin.getDescription()==null)){
                            pinInfo.append("Description: " + pin.getDescription() + "\n");
                        }
                        if (LoginManager.getInstance().getPermission() == 3){
                            pinInfo.append("Latitude: " + pin.getLat() + "\n");
                            pinInfo.append("Longitude: " + pin.getLong() + "\n");
                        }
                        Glide.with(PinInformationActivity.this)
                                .load(pin.getImageUrl())
                                .into(imageView);
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

        sendButton.setOnClickListener(v -> {
            if (commentSendBox.getText() != null){
                String comment = String.valueOf(commentSendBox.getText());
                WebSocketManager.getInstance().sendComment(comment);
                commentView.append(comment + "\n");
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (deleteEditBox.getText() != null) {
                int numDel = Integer.parseInt(String.valueOf(deleteEditBox.getText()));
                WebSocketManager.getInstance().sendComment("del " + numDel);
                commentView.clearComposingText();
                WebSocketManager.getInstance().closeCommentConnection();
                startActivity(getIntent());
            }
        });

        deletePinButton.setOnClickListener(v -> {
            if (adminPerms){
                pinVolley.deletePin(passedPinId, new pinVolley.DeletePinCallback() {
                    @Override
                    public void onSuccess() {
                        finish();
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e(TAG, "deletePin Failure: " + errorMessage);
                    }
                });
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
