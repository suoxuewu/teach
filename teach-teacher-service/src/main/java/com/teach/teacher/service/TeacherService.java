package com.teach.teacher.service;

import com.teach.bean.EduTeacher;
import com.teach.bean.EduUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    /**
     * 获取所有的教师
     * @return
     */
    List<EduTeacher> getAll();

    /**
     * 按照条件获取所有的教师
     * @param paramMap
     * @return
     */
    List<EduTeacher> selectListByCondition(Map<String, Object> paramMap);

    /**
     * 增加一个讲师
     * @param paramMap
     */
    void add(Map<String, Object> paramMap);

    /**
     * 获取一个讲师
     * @param userId
     * @return
     */
    EduTeacher getOne(String userId);

    /**
     * 更新一个讲师
     * @param paramMap
     */
    void update(Map<String, Object> paramMap);
}
