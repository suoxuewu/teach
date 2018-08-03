package com.teach.service;

import com.teach.bean.SysSubject;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    /**
     * 查询所有
     * @return
     */
    List<SysSubject> selectAll();

    /**
     * 修改ztree节点的级别
     * @param paramMap
     * @return
     */
    String modifySort(Map<String, String> paramMap);

    /**
     * 修改ztree节点名字
     * @param paramMap
     * @return
     */
    String modifyName(Map<String, String> paramMap);
}
