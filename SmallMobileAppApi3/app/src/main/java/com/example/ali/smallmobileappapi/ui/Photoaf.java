package com.example.ali.smallmobileappapi.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ali.smallmobileappapi.R;
import com.example.ali.smallmobileappapi.dataalbum.Photoadapter;
import com.example.ali.smallmobileappapi.dataphoto.Photo;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
// class  display  a photo full screen
public class Photoaf extends AppCompatActivity {
    private String idphoto;// id use to find the 
    private ImageView imv;
    private String src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoaf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imv = (ImageView) findViewById(R.id.imageaf);
        idphoto =(String) getIntent().getStringExtra("id_photo");
        OnClick();
    }

    public void OnClick(){


        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+idphoto+"/",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject jsonObject =  response.getJSONObject();
                        try {
                            JSONArray array = jsonObject.getJSONArray("images");
                            for(int i = 0; i <= 1; i++) {
                                JSONObject onephoto = array.getJSONObject(i);
                                src =(String) onephoto.getString("source");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Picasso.with(Photoaf.this).load(src).into(imv);
                    }
                }
        ).executeAsync();
    }

}
