package com.teach.controller;

import com.teach.bean.SysSubject;
import com.teach.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/selectAll")
    public List<SysSubject> getAll() {
        List<SysSubject> subjectList = subjectService.selectAll();
        return subjectList;
    }

    @RequestMapping("/modify")
    public String change(@RequestParam Map<String, String> paramMap){

       String flag = subjectService.modifySort(paramMap);
       return flag;
    }

    @RequestMapping("/change")
    public String changeName(@RequestParam Map<String, String> paramMap){

        String flag = subjectService.modifyName(paramMap);
        return flag;
    }
}
