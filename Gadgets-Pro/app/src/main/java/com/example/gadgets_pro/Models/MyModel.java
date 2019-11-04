package com.myapp.ytvideos.Models;

public class MyModel
{
    private String title,url,id;

    public MyModel(String title, String url, String id) {
        this.title = title;
        this.url = url;
        this.id = id;
    }

    public MyModel(){}

    public String getTitle() {
        return title;
    }


    public String getUrl() {
        return url;
    }


    public String getId() {
        return id;
    }

}
