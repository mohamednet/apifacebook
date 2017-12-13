package com.example.ali.smallmobileappapi.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smallmobileappapi.MainActivity;
import com.example.ali.smallmobileappapi.R;
import com.example.ali.smallmobileappapi.dataalbum.Album;
import com.example.ali.smallmobileappapi.dataalbum.Albumadapter;
import com.example.ali.smallmobileappapi.dataalbum.Photoadapter;
import com.example.ali.smallmobileappapi.dataphoto.Photo;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;

import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListPhoto extends AppCompatActivity {
   private String album_id; // the id of album that we will use it to  find the photo of album
   private String values;
   private TextView txt;
   private List<String> url_imagealbum; // list of image  from   album

    private RecyclerView recyclerView; // I  choose to work with  recyclerView because it's fast then listview
    private Photoadapter photoadapter;// the recycler view adapter we'll use it to gather the albums
    final ArrayList<Photo> photos =  new ArrayList<Photo>();// list of photo for each album will fill it from the facebook graph
    Button btnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the value of album_id
        album_id =(String) getIntent().getStringExtra("album_id");
        //call get the facebook  graph
        OnClick();

        //call function to download photo from facebook album
        btnd = (Button) findViewById(R.id.buttondawn);
        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Photo x:photos) {
                    Downloadtasl downloadtasl =  new Downloadtasl(x.getId());
                    downloadtasl.execute(x.getUrl_photo());
                }

            }
        });

    }
    String src ;

    // function get the facebook  graph
    public void OnClick(){
        Bundle parameters = new Bundle();
        parameters.putString("fields", "images"); // use facebook graph parameters
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                // use the album_id to get Its own photo
                "/"+album_id+"/photos",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject jsonObject =  response.getJSONObject();
                        try {
                            JSONArray array = jsonObject.getJSONArray("data");
                            for(int i = 0; i < array.length(); i++) {
                                JSONObject onephoto = array.getJSONObject(i);
                                //get your values
                                // this will return you the album's name.*/
                                try {
                                    JSONArray aray = onephoto.getJSONArray("images");
                                    for(int j = 0; j<= 1; j++) {
                                        JSONObject onphoto = aray.getJSONObject(j);
                                        src =(String) onphoto.getString("source");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                photos.add( new Photo(onephoto.getString("id"),src));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //display the photos in a recyclerView
                        recyclerView = (RecyclerView) findViewById(R.id.rv_photolist);
                        photoadapter = new Photoadapter(ListPhoto.this,photos);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ListPhoto.this));
                        recyclerView.setAdapter(photoadapter);
                    }
                }
        ).executeAsync();
    }





    // use AsyncTask to download  album  photos from facebook  to DCIM
    File new_folder;
    File inpute_file;
    public class Downloadtasl extends AsyncTask<String,Integer,String> {
        String textView;

        public Downloadtasl(String textView) {
            this.textView = textView;
        }

        // declare a progressDialog
        ProgressDialog progressDialog;

        // function download  a photo
        @Override
        protected String doInBackground(String... param) {
            String path = param[0];
            int file_lenght = 0;
            try {
                // url of photo
                URL url =  new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_lenght = urlConnection.getContentLength();

                //create a new folder to put in it photo download it from album
                new_folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"facebookphoto");
                // test if exist the folder after creating
                if(!new_folder.exists()){
                    new_folder.mkdir();
                }
                // create a new file .jpg to put the photo download it , and name it as her ID
                inpute_file =  new File(new_folder,textView+".jpg");

                // download the photo
                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                OutputStream outputStream = new FileOutputStream(inpute_file);
                while((count=inputStream.read(data))!=-1){
                    total+=count;
                    outputStream.write(data,0,count);
                    int prograss = (int)total*100/file_lenght;
                    publishProgress(prograss);
                }
                inputStream.close();
                outputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "Waiting Download complete.....";
        }

        // on pre execute
        @Override
        protected void onPreExecute() {
            progressDialog =  new ProgressDialog(ListPhoto.this); //
            progressDialog.setTitle("Download in Progress.....");// title to the progress
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//progress style
            progressDialog.setMax(100);//max value
            progressDialog.setProgress(0);// min values
            progressDialog.show();// display  the progress

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            String path = inpute_file.getPath();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }
    }
}
