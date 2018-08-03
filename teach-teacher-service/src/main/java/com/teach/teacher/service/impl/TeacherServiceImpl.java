package com.teach.teacher.service.impl;

import com.teach.bean.EduTeacher;
import com.teach.bean.SysSubject;
import com.teach.teacher.mapper.EduTeacherMapper;
import com.teach.teacher.mapper.SysSubjectMapper;
import com.teach.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Autowired
    private SysSubjectMapper sysSubjectMapper;

    @Override
    public List<EduTeacher> getAll() {
        List<EduTeacher> teachers = eduTeacherMapper.selectAll();
        return teachers;
    }

    @Override
    public List<EduTeacher> selectListByCondition(Map<String, Object> paramMap) {
        String name = (String) paramMap.get("name");
        String status = (String) paramMap.get("status");
        String createTime = (String) paramMap.get("createTime");
        String endTime = (String) paramMap.get("endTime");
        Example example = new Example(EduTeacher.class);

        Example.Criteria criteria = example.createCriteria();
        if(name != null && name.length()!=0){
            criteria.andEqualTo("name", name);
        }
        if(status!=null && status.length()!=0){
            criteria.andEqualTo("isStar", status);
        }
        if(createTime != null && createTime.length()!=0){
            criteria.andGreaterThanOrEqualTo("createTime",createTime);
        }
        if(endTime!=null && endTime.length()!=0){
            criteria.andLessThanOrEqualTo("createTime",endTime);
        }

        List<EduTeacher> userList = eduTeacherMapper.selectByExample(example);

        return userList;
    }

    @Override
    public void add(Map<String, Object> paramMap) {

        String name = (String) paramMap.get("name");
        String education = (String) paramMap.get("education");
        String subject = (String) paramMap.get("subject");
        String range = (String) paramMap.get("range");
        String order = (String) paramMap.get("order");
        String desc = (String) paramMap.get("desc");
        String imageUrl = (String) paramMap.get("imageUrl");
        EduTeacher eduTeacher = new EduTeacher();
        eduTeacher.setName(name);
        eduTeacher.setEducation(education);
        eduTeacher.setSubjectId(Integer.parseInt(subject));
        eduTeacher.setIsStar(Integer.parseInt(range));
        eduTeacher.setSort(Integer.parseInt(order));
        eduTeacher.setCareer(desc);
        eduTeacher.setPicPath(imageUrl);
        eduTeacher.setCreateTime(new Date());

        eduTeacherMapper.insertSelective(eduTeacher);
    }

    @Override
    public EduTeacher getOne(String userId) {
        EduTeacher eduTeacher = new EduTeacher();
        eduTeacher.setId(Integer.parseInt(userId));
        EduTeacher teacher = eduTeacherMapper.selectByPrimaryKey(eduTeacher);
        return teacher;
    }

    @Override
    public void update(Map<String, Object> paramMap) {
        String userId = (String) paramMap.get("userId");
        String name = (String) paramMap.get("name");
        String education = (String) paramMap.get("education");

        String subjectStr = (String) paramMap.get("subject");
        Example example = new Example(SysSubject.class);
        example.createCriteria().andEqualTo("subjectName",subjectStr);
        SysSubject subject = sysSubjectMapper.selectOneByExample(example);

        String range = (String) paramMap.get("range");
        String order = (String) paramMap.get("order");
        String desc = (String) paramMap.get("desc");
        String imageUrl = (String) paramMap.get("imageUrl");

        EduTeacher eduTeacher = new EduTeacher();
        eduTeacher.setId(Integer.parseInt(userId));
        eduTeacher.setName(name);
        eduTeacher.setEducation(education);
        eduTeacher.setSubjectId(subject.getSubjectId());
        eduTeacher.setIsStar(Integer.parseInt(range));
        eduTeacher.setSort(Integer.parseInt(order));
        eduTeacher.setCareer(desc);
        eduTeacher.setPicPath(imageUrl);
        eduTeacher.setUpdateTime(new Date());

        eduTeacherMapper.updateByPrimaryKeySelective(eduTeacher);
    }
}
