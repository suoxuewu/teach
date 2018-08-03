package com.teach.service.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teach.bean.EduUser;
import com.teach.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@Controller
public class StudentController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
//    @ResponseBody
    public PageInfo<EduUser> getListUser(@RequestParam("pageNO") Integer pageNo) {

        Map<String, Object> resultMap = new HashMap<>();
        PageHelper.startPage(pageNo, 5);

        List<EduUser> userList = userService.selectList();

        //分页
        PageInfo<EduUser> pageInfo = new PageInfo<>(userList, 5);

        return pageInfo;
    }


    @RequestMapping("/listCondition")
    public PageInfo<EduUser> getListByCondition(@RequestParam Map<String, Object> paramMap) {

        String pageNO = (String) paramMap.get("pageNO");
        if (pageNO == null || pageNO.length() == 0) {
            pageNO = "1";
        }
        PageHelper.startPage(Integer.parseInt(pageNO), 5);
        List<EduUser> uesrList = userService.selectListByCondition(paramMap);

        //分页
        PageInfo<EduUser> pageInfo = new PageInfo<>(uesrList, 5);

        return pageInfo;
    }

    @RequestMapping("/modifyState")
    public String changeState(@RequestParam(value = "userId") String userId){

        return userService.modifyState(userId);
    }

    @RequestMapping("/modifyPwd")
    public String changePwd(@RequestParam("userId")String userId,@RequestParam(value = "pwd")String pwd){
        String result = userService.modifyPwd(userId,pwd);
        return result;
    }

    @RequestMapping("/getAll")
    public List<EduUser> getAll(){
       return userService.getAll();
    }

    @RequestMapping("/batch")
    public void batchSave(@RequestBody List<EduUser> list){
        userService.batchSave(list);
    }
}
