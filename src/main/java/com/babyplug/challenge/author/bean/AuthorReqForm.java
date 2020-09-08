package com.babyplug.challenge.author.bean;

import java.io.Serializable;
import java.util.List;

public class AuthorReqForm implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
