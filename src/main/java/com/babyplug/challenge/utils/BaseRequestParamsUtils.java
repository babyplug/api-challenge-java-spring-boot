package com.babyplug.challenge.utils;

import java.io.Serializable;

public abstract class BaseRequestParamsUtils implements Serializable {
    private static final long serialVersionUID = 1L;

    private int page;
    private boolean showAll;
    private Integer size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
