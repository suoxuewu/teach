package com.teach.teacher.controller;

import com.github.pagehelper.PageInfo;
import com.teach.bean.EduTeacher;
import com.teach.teacher.TeacherService;
import feign.Feign;
import feign.codec.StringDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/index")
    public String index(Map<String, Object> result, @RequestParam(value = "pageNO", defaultValue = "1") Integer pageNo) {


        PageInfo<EduTeacher> pageInfo = teacherService.getAllTeachers(pageNo);
        result.put("pageInfo", pageInfo);

        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("1", "高级讲师");
        selectMap.put("2", "首席讲师");
        result.put("selectMap", selectMap);

        return "index";
    }

    @RequestMapping("/condition")
    public String getByCondition(@RequestParam Map<String, Object> paramMap, Map<String, Object> result) {
        String name = (String) paramMap.get("name");
        String status = (String) paramMap.get("status");
        String createTime = (String) paramMap.get("createTime");
        String endTime = (String) paramMap.get("endTime");

        String queryUrl = "name=" + name + "&status=" + status + "&createTime=" + createTime + "&endTime=" + endTime;
        result.put("queryUrl", queryUrl);

        PageInfo<EduTeacher> pageInfo = teacherService.getListByCondition(paramMap);
        result.put("pageInfo", pageInfo);

        Map<String, String> selectMap = new HashMap<>();
        selectMap.put("1", "高级讲师");
        selectMap.put("2", "首席讲师");
        result.put("selectMap", selectMap);


        return "condition";
    }

    @RequestMapping("/add")
    public String add(Map<String, Object> resultMap, @RequestParam(value = "userId", required = false) String userId) {
        //添加页面
        EduTeacher eduTeacher = null;
        if (userId != null) {
            eduTeacher = teacherService.getOneTeacher(userId);
        }
        resultMap.put("teacher", eduTeacher);
        return "add";
    }

    @RequestMapping("/change")
    public String chagne(@RequestParam Map<String, Object> paramMap, @RequestParam(value = "file", required = false) MultipartFile file) {
        String userId = (String) paramMap.get("userId");
        String pageNo = (String) paramMap.get("pageNo");
        if (userId == null || userId.length() == 0) {
            //新增加
            paramMap.put("isUpdate", false);
            pageNo = "9999";
        } else {
            //修改
            paramMap.put("isUpdate", true);
        }
        teacherService.upload(paramMap, file);
        return "redirect:/teacher/index?pageNO=" + pageNo;
    }
}
