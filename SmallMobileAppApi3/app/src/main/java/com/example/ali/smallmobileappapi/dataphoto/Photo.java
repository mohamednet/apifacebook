package com.example.ali.smallmobileappapi.dataphoto;

/**
 * Created by 'Ali' on 04/12/2017.
 */

public class Photo {
    private String id;
    private String Url_photo;

    public Photo(String id, String url_photo) {
        this.id = id;
        Url_photo = url_photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_photo() {
        return Url_photo;
    }

    public void setUrl_photo(String url_photo) {
        Url_photo = url_photo;
    }
}
