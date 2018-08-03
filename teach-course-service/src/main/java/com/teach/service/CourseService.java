package com.teach.service;

import com.teach.bean.EduCourse;

import java.util.List;

public interface CourseService {
    /**
     *获取所有的课程
     * @return
     */
    List<EduCourse> getAll();

    /**
     * 获取一个课程信息
     * @param courseId
     * @return
     */
    EduCourse seletOne(String courseId);
}
