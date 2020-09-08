package com.babyplug.challenge.album.bean;

import java.io.Serializable;
import java.util.List;

public class AlbumReqForm implements Serializable {
    private String name;
    private List<Long> photoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Long> photoList) {
        this.photoList = photoList;
    }
}
