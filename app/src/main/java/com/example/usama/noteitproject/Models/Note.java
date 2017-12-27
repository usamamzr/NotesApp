package com.example.usama.noteitproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Usama on 11/26/2017.
 */

public class Note {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String head;

    @SerializedName("body")
    @Expose
    private String desc;
    //public String head, desc;

    public Note() {
        this.id = id;
        this.head = head;
        this.desc = desc;
    }

    /*@Override
    public String toString() {
        return "Note{" +
                "head='" + head + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
