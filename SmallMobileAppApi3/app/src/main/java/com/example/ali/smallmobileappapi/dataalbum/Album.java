package com.example.ali.smallmobileappapi.dataalbum;

/**
 * Created by 'mohamed' on 27/11/2017.
 */

public class Album {


     private String created_time;
     private String id;
     private String name;
     private String cover_photourl;

     public Album(String created_time, String id, String name, String cover_photourl) {
          this.created_time = created_time;
          this.id = id;
          this.name = name;
          this.cover_photourl = cover_photourl;
     }

     public void setCover_photourl(String cover_photourl) {this.cover_photourl = cover_photourl;}
     public void setCreated_time(String created_time) {
          this.created_time = created_time;
     }
     public void setName(String name) {
          this.name = name;
     }
     public void setId(String id) {
          this.id = id;
     }
     public String getName() {
          return name;
     }
     public String getId() {return id;}
     public String getCreated_time() {return created_time;}
     public String getCover_photourl() {return cover_photourl;}


}
