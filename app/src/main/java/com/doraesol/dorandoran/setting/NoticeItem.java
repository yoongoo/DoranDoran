package com.doraesol.dorandoran.setting;

import java.util.ArrayList;

/**
 * Created by YOONGOO on 2017-02-10.
 */

public class NoticeItem {

    public String title;
    public ArrayList<String> childList;

    public NoticeItem(String title) {
        this.title = title;
        this.childList = new ArrayList<>();
    }

}
