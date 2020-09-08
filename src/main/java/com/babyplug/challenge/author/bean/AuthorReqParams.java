package com.babyplug.challenge.author.bean;

import com.babyplug.challenge.utils.BaseRequestParamsUtils;

import java.util.List;

public class AuthorReqParams extends BaseRequestParamsUtils {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
