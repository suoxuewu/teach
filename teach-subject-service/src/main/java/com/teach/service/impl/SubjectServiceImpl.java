package com.teach.service.impl;

import com.teach.bean.SysSubject;
import com.teach.mapper.SubjectMapper;
import com.teach.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<SysSubject> selectAll() {

        Example example = new Example(SysSubject.class);
        example.setOrderByClause("sort desc");

        List<SysSubject> list = subjectMapper.selectByExample(example);
        return list;
    }

    @Override
    public String modifySort(Map<String, String> paramMap) {

        //新节点设置sort失败
        String id = paramMap.get("id");
        String value = paramMap.get("value");

        SysSubject subject = new SysSubject();
        subject.setSubjectId(Integer.parseInt(id));
        subject.setSort(Integer.parseInt(value));

        int count = subjectMapper.updateByPrimaryKeySelective(subject);

        if (count > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public String modifyName(Map<String, String> paramMap) {

        SysSubject sysSubject = new SysSubject();
        //区分新旧节点
        String id = paramMap.get("id");
        String subjectName = paramMap.get("subjectName");

        String parentId = paramMap.get("parentId");
        if (parentId != null && parentId.length() != 0) {
            sysSubject.setParentId(Integer.parseInt(parentId));
        }

        sysSubject.setSubjectName(subjectName);
        sysSubject.setCreateTime(new Date());
        int count = 0;
        if (id != null && id.length() != 0) {
            //旧节点更新
            sysSubject.setSubjectId(Integer.parseInt(id));
            count = subjectMapper.updateByPrimaryKeySelective(sysSubject);
        } else {
            //新节点添加
            sysSubject.setSubjectId(null);
            count = subjectMapper.insertSelective(sysSubject);
        }

        if (count > 0) {
            return "success";
        }
        return "fail";
    }
}
