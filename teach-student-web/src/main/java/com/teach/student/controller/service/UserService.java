package com.teach.student.controller.service;

import com.github.pagehelper.PageInfo;
import com.teach.bean.EduUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("teach-student-service")
public interface UserService {
    /**
     * 获取所有的学生
     */
    @RequestMapping("/list")
    public PageInfo<EduUser> getListUser(@RequestParam("pageNO") Integer pageNo);

    /**
     * 按照查询条件获取所有的学生
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/listCondition")
    public PageInfo<EduUser> getListByCondition(@RequestParam Map<String, Object> paramMap);

    /**
     * 改变账户状态
     *
     * @param userId
     * @return
     */
    @RequestMapping("/modifyState")
    String changeState(@RequestParam(value = "userId") String userId);

    /**
     * 改变密码
     * @param pwd
     * @return
     */
    @RequestMapping("/modifyPwd")
    String changePwd(@RequestParam("userId")String userId,@RequestParam(value = "pwd")String pwd);

    /**
     * 获取所有的学生
     * @return
     */
    @RequestMapping("/getAll")
    List<EduUser> getAll();

    /**
     * 批量保存
     * @param list
     */
    @RequestMapping("/batch")
    void batchSave(@RequestBody List<EduUser> list);
}
