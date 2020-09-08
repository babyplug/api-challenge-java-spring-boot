package com.babyplug.challenge.album.bean;

import com.babyplug.challenge.utils.BaseRequestParamsUtils;

import java.util.List;

public class AlbumReqParams extends BaseRequestParamsUtils {
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
