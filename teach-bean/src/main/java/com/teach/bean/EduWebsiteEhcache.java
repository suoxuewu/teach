package com.teach.bean;

public class EduWebsiteEhcache {
    private Integer id;

    private String ehcacheKey;

    private String ehcacheDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEhcacheKey() {
        return ehcacheKey;
    }

    public void setEhcacheKey(String ehcacheKey) {
        this.ehcacheKey = ehcacheKey == null ? null : ehcacheKey.trim();
    }

    public String getEhcacheDesc() {
        return ehcacheDesc;
    }

    public void setEhcacheDesc(String ehcacheDesc) {
        this.ehcacheDesc = ehcacheDesc == null ? null : ehcacheDesc.trim();
    }
}