package com.teach.teacher;

import com.github.pagehelper.PageInfo;
import com.teach.bean.EduTeacher;
import com.teach.bean.EduUser;
import com.teach.teacher.config.FeignMultipartSupportConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(value = "teach-teacher-service",configuration = FeignMultipartSupportConfig.class)
public interface TeacherService {

    /**
     * 获取所有的教师
     * @return
     */
    @RequestMapping("/getAll")
    PageInfo<EduTeacher> getAllTeachers(@RequestParam("pageNO") Integer pageNo);

    /**
     * 按照条件获取所有的教师
     * @param paramMap
     * @return
     */
    @RequestMapping("/getCondition")
    PageInfo<EduTeacher> getListByCondition(@RequestParam Map<String, Object> paramMap);

    /**
     * 增加一个讲师
     * @param paramMap
     * @param file
     */

    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST,consumes = MULTIPART_FORM_DATA_VALUE)
    void upload(@RequestParam Map<String, Object> paramMap, @RequestPart(value = "file",required = false) MultipartFile file);

    /**
     * 根据Id获取一个用户的信息
     * @param userId
     * @return
     */
    @RequestMapping("/getOne")
    EduTeacher getOneTeacher(@RequestParam(value = "userId")String userId);
}
