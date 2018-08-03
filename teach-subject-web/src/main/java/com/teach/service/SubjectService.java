package com.teach.service;

import com.teach.bean.SysSubject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("teach-subject-service")
public interface SubjectService {

    /**
     * 获取所有
     * @return
     */
    @RequestMapping("/selectAll")
    List<SysSubject> getAll();

    /**
     * 修改ztree节点的级别
     * @param paramMap
     * @return
     */
    @RequestMapping("/modify")
    String change(@RequestParam  Map<String, String> paramMap);

    /**
     * 修改ztree节点名字
     * @param paramMap
     * @return
     */
    @RequestMapping("/change")
    String changeName(@RequestParam Map<String, String> paramMap);
}
