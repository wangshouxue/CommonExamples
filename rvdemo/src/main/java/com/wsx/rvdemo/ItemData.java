package com.wsx.rvdemo;

import java.io.Serializable;

public class ItemData implements Serializable {
    private String mTitle;

    public ItemData(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }
}
