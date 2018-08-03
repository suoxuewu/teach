package com.teach.bean;

import java.util.Date;

public class EduHelpMenu {
    private Integer id;

    private Integer parentid;

    private String name;

    private Date createTime;

    private Integer sort;

    private String varchar;

    private String linkBuilding;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVarchar() {
        return varchar;
    }

    public void setVarchar(String varchar) {
        this.varchar = varchar == null ? null : varchar.trim();
    }

    public String getLinkBuilding() {
        return linkBuilding;
    }

    public void setLinkBuilding(String linkBuilding) {
        this.linkBuilding = linkBuilding == null ? null : linkBuilding.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}