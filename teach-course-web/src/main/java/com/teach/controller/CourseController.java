package com.teach.controller;

import com.github.pagehelper.PageInfo;
import com.teach.bean.EduCourse;
import com.teach.bean.SysSubject;
import com.teach.service.CourseService;
import com.teach.service.SysSubjectService;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@RequestMapping("/course")
@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SysSubjectService sysSubjectService;

    @RequestMapping("/index")
    public String index(Map<String,Object> result, @RequestParam(value = "pageNO" ,defaultValue = "1") Integer pageNo){


        PageInfo<EduCourse> pageInfo = courseService.getPageInfo(pageNo);


        for (EduCourse eduCourse : pageInfo.getList()) {
            Date endTime = eduCourse.getEndTime();
            Date currentTime = new Date();
            if(eduCourse.getLosetype() == 1){
                //转换为天数
                long time =  endTime.getTime() - currentTime.getTime();
                long day = time / (1000 * 60 * 60 * 24) == 0 ? time / (1000 * 60 * 60 * 24):time / (1000 * 60 * 60 * 24)+1;
                eduCourse.setEndDay(day);
            }

        }

        result.put("pageInfo",pageInfo);

        //查询所有的专业
        List<SysSubject> subjects = sysSubjectService.getAll();
        result.put("subjectList",subjects);


        //上下架状态
        Map<String,String> statusMap = new HashMap<>();
        statusMap.put("1","上架");
        statusMap.put("2","下架");
        result.put("statusMap",statusMap);

        return "index";
    }

    @RequestMapping("/add")
    public String add(@RequestParam Map<String,String> paramMap,Map<String,Object> result){
        //courseId="+courseId+"&pageNO="+[[${pageInfo.pageNum}]];

        String courseId = paramMap.get("courseId");
        //当前的课程信息
        EduCourse course = courseService.getOne(courseId);
        if(course.getLosetype() == 1){
            Date endTime = course.getEndTime();
            Date currentTime = new Date();
            //转换为天数
            long time =  endTime.getTime() - currentTime.getTime();
            long day = time / (1000 * 60 * 60 * 24) == 0 ? time / (1000 * 60 * 60 * 24):time / (1000 * 60 * 60 * 24)+1;
            course.setEndDay(day);
        }
        result.put("course",course);
        //查询所有的专业
        List<SysSubject> subjects = sysSubjectService.getAll();
        result.put("subjectList",subjects);


        //上下架状态
        Map<String,String> statusMap = new HashMap<>();
        statusMap.put("1","上架");
        statusMap.put("2","下架");
        result.put("statusMap",statusMap);
        return "add";
    }
}
