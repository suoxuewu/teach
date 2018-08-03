package com.teach.controller;

import com.teach.bean.SysSubject;
import com.teach.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/index")
    public String index() {

        return "index";
    }

    @RequestMapping("/zt")
    @ResponseBody
    public Map<String, Object> ztree() {
        List<SysSubject> subjects = subjectService.getAll();
        Map<String, Object> resultMap = new HashMap<>();
        List<SysSubject> parentList = new ArrayList<>();
        for (SysSubject subject : subjects) {
            if (subject.getParentId() == 0) {
                //父节点
                ArrayList<SysSubject> list = new ArrayList<>();

                for (SysSubject sysSubject : subjects) {
                    System.out.println(sysSubject.getParentId() + "==============" + subject.getSubjectId());
                    if (sysSubject.getParentId().equals(subject.getSubjectId())) {
                        //子节点
                        list.add(sysSubject);
                    }
                }
                subject.setChildren(list);
                parentList.add(subject);
            }
        }

        resultMap.put("data", parentList);
        return resultMap;
    }

    @RequestMapping("/changeSort")
    @ResponseBody
    public String change(@RequestParam Map<String, String> paramMap) {

        String flag = subjectService.change(paramMap);

        return flag;
    }

    @RequestMapping("/rename")
    @ResponseBody
    public String rename(@RequestParam Map<String, String> paramMap) {

        String flag = subjectService.changeName(paramMap);

        return flag;
    }


}
