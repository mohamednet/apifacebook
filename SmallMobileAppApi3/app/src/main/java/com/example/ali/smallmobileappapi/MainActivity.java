package com.example.ali.smallmobileappapi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.smallmobileappapi.ui.AlbumListActivity;
import com.example.ali.smallmobileappapi.ui.ListPhoto;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {


    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    //Calling the facebook Api
    String url_image = "https://scontent.xx.fbcdn.net/v/t31.0-8/24059386_1573809639329602_8459267437357410582_o.jpg?oh=0ac39a634227000c44d8062018a6f8ad&oe=5A98ECA7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());//Initlalisation of facebook SDK
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        textView = (TextView) findViewById(R.id.textview);
        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                MethodeClick();
            }

            @Override
            public void onCancel() {

                textView.setText("login cancelled !");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });//Checking facebook credentials If true a user logs into his account else an error message occurs login cancelled





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            LoginManager.getInstance().logOut(); // logout from facebook
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //go to the  AlbumListActivity class which lists  facebook albums
    public void MethodeClick() {
        Intent intent =  new Intent(this, AlbumListActivity.class);
        startActivity(intent);
    }
}
