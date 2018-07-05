package com.zhaolong.lesson4.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "good")
public class GoodEntity implements Serializable {
    @Id
    @Column(name = "guid")
    private String guid;

    @Column(name = "title")
    public String title;

    @Column(name = "time")
    public Date time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typeGuid")
    public GoodTypeEntity typeGuid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public GoodTypeEntity getTypeGuid() {
        return typeGuid;
    }

    public void setTypeGuid(GoodTypeEntity typeGuid) {
        this.typeGuid = typeGuid;
    }
}
