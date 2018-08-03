package com.teach.service;

import com.teach.bean.SysSubject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("teach-subject-service")
public interface SysSubjectService {

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/selectAll")
    public List<SysSubject> getAll();
}
