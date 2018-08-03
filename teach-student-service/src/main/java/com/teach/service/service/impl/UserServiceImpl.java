package com.teach.service.service.impl;

import com.teach.bean.EduUser;
import com.teach.service.mapper.EduUserMapper;
import com.teach.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EduUserMapper eduUserMapper;

    @Override
    public List<EduUser> selectList() {
        return eduUserMapper.selectAll();
    }

    @Override
    public List<EduUser> selectListByCondition(Map<String, Object> paramMap) {

        String condition = (String) paramMap.get("condition");
        String status = (String) paramMap.get("status");
        String createTime = (String) paramMap.get("createTime");
        String endTime = (String) paramMap.get("endTime");

        Example example = new Example(EduUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(condition != null && condition.length() !=0){
            criteria.orEqualTo("mobile", condition)
                    .orEqualTo("email", condition)
                    .orEqualTo("showName", condition)
                    .orEqualTo("userName", condition);
        }
        if(status != null && status.length() !=0) {
            criteria.andEqualTo("isAvalible", status);
        }
        if(createTime != null && createTime.length() !=0 ) {
            criteria.andGreaterThanOrEqualTo("createTime",createTime);
        }
        if(endTime != null && endTime.length() !=0 ) {
            criteria.andLessThanOrEqualTo("createTime",endTime);
        }


        List<EduUser> userList = eduUserMapper.selectByExample(example);

        return userList;
    }

    @Override
    public String modifyState(String userId) {

        EduUser eduUser = new EduUser();
        eduUser.setUserId(Integer.parseInt(userId));

        eduUser = eduUserMapper.selectByPrimaryKey(eduUser);


        eduUser.setIsAvalible(!eduUser.getIsAvalible());
        eduUserMapper.updateByPrimaryKeySelective(eduUser);

        eduUser = eduUserMapper.selectByPrimaryKey(eduUser);

        if (eduUser.getIsAvalible()) {
            return "normal";
        }
        return "freeze";
    }

    @Override
    public String modifyPwd(String userId, String pwd) {

        String password = DigestUtils.md5DigestAsHex(pwd.getBytes());

        EduUser eduUser = new EduUser();
        eduUser.setUserId(Integer.parseInt(userId));
        eduUser.setPassword(password);

        int count = eduUserMapper.updateByPrimaryKeySelective(eduUser);
        if (count > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public List<EduUser> getAll() {
        return eduUserMapper.selectAll();
    }

    @Override
    public void batchSave(List<EduUser> list) {
        eduUserMapper.batchSave(list);
    }


}
