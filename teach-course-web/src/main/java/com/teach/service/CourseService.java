package com.teach.service;

import com.github.pagehelper.PageInfo;
import com.teach.bean.EduCourse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("teach-course-service")
public interface CourseService {


    /**
     * 获取所有课程信息
     * @return
     */
    @RequestMapping("/list")
    PageInfo<EduCourse> getPageInfo(@RequestParam("pageNO") Integer pageNo);

    @RequestMapping("/getOne")
    EduCourse getOne(@RequestParam("courseId") String courseId);
}
