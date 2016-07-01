package com.example.dell.zhihu.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2016/6/28.
 */
public  class StoryBean implements Serializable{
    private int type;
    private int id;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}