package com.teach.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysSubject implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;
    @Column
    private String subjectName;
    @Column
    private Boolean status;
    @Column
    private Date createTime;
    @Column
    private Integer parentId;
    @Column
    private Integer sort;

    @Transient
    private List<SysSubject> children;

    @Transient
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SysSubject> getChildren() {
        return children;
    }

    public void setChildren(List<SysSubject> children) {
        this.children = children;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
        this.id = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}