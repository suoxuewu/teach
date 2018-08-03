package com.teach.service.service;

import com.teach.bean.EduUser;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<EduUser> selectList();

    List<EduUser> selectListByCondition(Map<String, Object> paramMap);

    String modifyState(String userId);

    String modifyPwd(String userId,String pwd);

    List<EduUser> getAll();

    void batchSave(List<EduUser> list);
}
