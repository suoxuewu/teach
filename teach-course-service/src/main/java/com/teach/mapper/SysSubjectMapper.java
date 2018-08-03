package com.teach.mapper;

import com.teach.bean.SysSubject;

public interface SysSubjectMapper {

    /**
     * 根据id查询一条数据
     * @return
     */
    SysSubject getOne(Integer id);
}
