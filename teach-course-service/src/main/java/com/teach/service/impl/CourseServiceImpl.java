package com.teach.service.impl;

import com.teach.bean.EduCourse;
import com.teach.mapper.EduCourseMapper;
import com.teach.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Override
    public List<EduCourse> getAll() {
        //还要查询出与此对应的专业
        List<EduCourse> courseList = eduCourseMapper.selectAll();

        return courseList;
    }

    @Override
    public EduCourse seletOne(String courseId) {


        return  eduCourseMapper.selectOne(courseId);
    }
}
