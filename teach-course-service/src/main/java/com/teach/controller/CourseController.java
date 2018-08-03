package com.teach.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.teach.bean.EduCourse;
import com.teach.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/list")
    public PageInfo<EduCourse> getPageInfo(@RequestParam("pageNO") Integer pageNo) {

        PageHelper.startPage(pageNo,5);
        List<EduCourse> courseList = courseService.getAll();
        PageInfo<EduCourse> pageInfo = new PageInfo<>(courseList, 5);

        return pageInfo;
    }

    @RequestMapping("/getOne")
    public EduCourse getOne(@RequestParam("courseId") String courseId){
        EduCourse eduCourse = courseService.seletOne(courseId);
        return eduCourse;
    }
}
