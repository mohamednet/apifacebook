package com.example.ali.smallmobileappapi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.ali.smallmobileappapi.R;
import com.example.ali.smallmobileappapi.dataalbum.Album;
import com.example.ali.smallmobileappapi.dataalbum.Albumadapter;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumListActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // I  choose to work with  recyclerView because it's fast then listview
    private Albumadapter albumadapter; // the recycler view adapter we'll use it to gather the albums
    final ArrayList<Album> albums =  new ArrayList<Album>(); // list of albums will fill it from the facebook graph

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_album_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //call get the facebook  graph
        OnClick();
    }
    // function get the facebook  graph
    public void OnClick(){

        albums.clear();
        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture{url},id,name,created_time");
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/albums",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        //getting the album data as a json file
                        JSONObject jsonObject =  response.getJSONObject();
                        try {

                            JSONArray array = jsonObject.getJSONArray("data");
                            for(int i = 0; i < array.length(); i++) {
                                JSONObject oneAlbum = array.getJSONObject(i);
                                albums.add(new Album(oneAlbum.getString("created_time"),oneAlbum.getString("id"),
                                        oneAlbum.getString("name"),
                                        oneAlbum.getJSONObject("picture").getJSONObject("data").getString("url")));
                                //get your values
                                // this will return you the album's
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //display the albums in a recyclerView
                        recyclerView = (RecyclerView) findViewById(R.id.rv_aLbumlist);
                        albumadapter = new Albumadapter(AlbumListActivity.this,albums);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AlbumListActivity.this));
                        recyclerView.setAdapter(albumadapter);
                    }
                }
        ).executeAsync();
    }

}
