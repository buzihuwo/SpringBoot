package com.zhaolong.lesson4.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "goodType")
public class GoodTypeEntity implements Serializable {
    @Id
    @Column(name = "guid")
    private String guid;

    @Column(name = "name")
    private String name;

    @Column(name = "isShow")
    public boolean isShow;

    @Column(name = "time")
    public Date time;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
