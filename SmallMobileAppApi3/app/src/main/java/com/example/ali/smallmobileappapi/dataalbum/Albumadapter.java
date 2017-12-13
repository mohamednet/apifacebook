package com.example.ali.smallmobileappapi.dataalbum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.smallmobileappapi.R;
import com.example.ali.smallmobileappapi.ui.ListPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 'Ali' on 30/11/2017.
 */
//the recycler object needs an adapter to display the album's data
public class Albumadapter extends RecyclerView.Adapter<Albumadapter.Viewholder>{



    //The AlbumAdapter needs a Viewholder to display the values from the xml file " album.xml"
    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

       // getting the values from the album.xml file to Viewholder
        TextView name;
        TextView   created_time;
        TextView   id;
        ImageView cover_photourl;
        List<Album> album =  new ArrayList<>();
        Context ctx;

        public Viewholder(View itemView,Context cntxt,List<Album> alb) {
            super(itemView);
            this.album = alb;
            this.ctx = cntxt;
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            created_time = (TextView) itemView.findViewById(R.id.created_time);
            id = (TextView) itemView.findViewById(R.id.id);
            cover_photourl = (ImageView) itemView.findViewById(R.id.cover_photourl);
        }


        // this function sends us to the activity of ListPhoto on click foreach object Viewholder
        @Override
        public void onClick(View view) {
            int position =  getAdapterPosition();
            Album album = this.album.get(position);
            Intent intent =  new Intent(this.ctx,ListPhoto.class);

            // send album_id value from this activity to ListPhoto activity
            intent.putExtra("album_id",album.getId());
            this.ctx.startActivity(intent);

        }
    }
    // declare the context and the list of albums
    private Context context;
    private List<Album> album;

    //binding the Albumadapter values  : Constructor
    public Albumadapter(Context c , List<Album> albumslist){
        this.context = c;
        this.album = albumslist;
    }

    // function return a view holder of 'album'
    @Override
    public Albumadapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.album,parent,false);
        return new Viewholder(v,context,album);
    }

    //Fill the viewholder values for each  album values with the position
    @Override
    public void onBindViewHolder(Albumadapter.Viewholder holder, int position) {
        Album albumpo =  album.get(position);
        holder.name.setText(albumpo.getName());
        // set image value from  url with picasso
        Picasso.with(context).load(albumpo.getCover_photourl()).into(holder.cover_photourl);
        holder.created_time.setText(albumpo.getCreated_time());
        holder.id.setText(albumpo.getId());
    }

    // get the size of album
    @Override
    public int getItemCount() {

        return album.size();
    }
}
