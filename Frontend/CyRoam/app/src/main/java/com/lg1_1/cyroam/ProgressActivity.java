package com.lg1_1.cyroam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProgressActivity extends AppCompatActivity {

    private Button postButton;
    private EditText pinIDText;
//    private dropDown pinSelector; //this is a better idea than an edittext but oh well

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        //todo initialize buttons and editTexts
//        pinIDText = findViewById()
        //etc


        //textbox that lets you type in a pin ID

    postButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });

    }

    private void getData(int ID, String progress){
        String url = MainActivity.url + "/progress"; //sets URL to progress table
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MainActivity.url+"/progress", null,
                response -> {
                    try{
                        JSONArray jsonArray = response.getJSONArray("progress");
                        Log.w("volley", "progress request success");

                        //for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject pinProgress = jsonArray.getJSONObject(ID);

                        int id = pinProgress.getInt("id");
                        Boolean pinBool = pinProgress.getBoolean("")
                        //}
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        queue.add(request);
    }
}