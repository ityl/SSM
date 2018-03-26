

package com.ssm.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class DbBaseObject<T> implements Serializable {
    private T id;
    @JsonIgnore
    private long creator;
    @JsonIgnore
    private Date modifyTime;
    @JsonIgnore
    private long modifier;

    @JsonIgnore
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DbBaseObject() {
        this.init();
    }

    protected void init() {
        long now = System.currentTimeMillis();
        this.setModifyTime(new Date(now));
    }

    public long getCreator() {
        return this.creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public long getModifier() {
        return this.modifier;
    }

    public void setModifier(long modifier) {
        this.modifier = modifier;
    }

    public T getId() {
        return this.id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
