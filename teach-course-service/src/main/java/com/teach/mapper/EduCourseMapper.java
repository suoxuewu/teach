package com.teach.mapper;


import com.teach.bean.EduCourse;
import com.teach.bean.SysSubject;

import java.util.List;

public interface EduCourseMapper{

    /**
     * 查询所有的课程和与之对应的专业
     * @return
     */
    List<EduCourse> selectAll();

    /**
     * 查询一个信息
     * @param courseId
     * @return
     */
    EduCourse selectOne(String courseId);
}
