package com.teach.teacher.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teach.bean.EduTeacher;
import com.teach.teacher.service.TeacherService;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Value("${fileServer.url}")
    private String fileUrl;


    @RequestMapping("/getAll")
    public PageInfo<EduTeacher> getAllTeachers(@RequestParam("pageNO") Integer pageNo) {
        PageHelper.startPage(pageNo, 5);
        List<EduTeacher> teachers = teacherService.getAll();
        PageInfo<EduTeacher> pageInfo = new PageInfo<>(teachers, 5);

        return pageInfo;
    }

    @RequestMapping("/getCondition")
    public PageInfo<EduTeacher> getListByCondition(@RequestParam Map<String, Object> paramMap) {
        String pageNO = (String) paramMap.get("pageNO");
        if (pageNO == null || pageNO.length() == 0) {
            pageNO = "1";
        }
        PageHelper.startPage(Integer.parseInt(pageNO), 5);
        List<EduTeacher> uesrList = teacherService.selectListByCondition(paramMap);

        //分页
        PageInfo<EduTeacher> pageInfo = new PageInfo<EduTeacher>(uesrList, 5);

        return pageInfo;

    }

    @RequestMapping("/addTeacher")
    void upload(@RequestParam Map<String, Object> paramMap, @RequestParam(value = "file", required = false) MultipartFile file) {
        String imageUrl = null;
        if(file.getOriginalFilename().length() != 0){
            imageUrl = uploadImage(file);
        }


        //添加一个图片路径 (paramMap)
        paramMap.put("imageUrl", imageUrl);
        String str = (String) paramMap.get("isUpdate");
        boolean isUpdate = Boolean.parseBoolean(str);
        if(isUpdate){
            teacherService.update(paramMap);
        }else {
            teacherService.add(paramMap);
        }


    }

    public String uploadImage(MultipartFile file) {

        try {

            String imgUrl = fileUrl;

            if (file != null) {

                String configFile = this.getClass().getResource("/tracker.conf").getFile();
                ClientGlobal.init(configFile);

                TrackerClient trackerClient = new TrackerClient();
                TrackerServer trackerServer = trackerClient.getConnection();

                StorageClient storageClient = new StorageClient(trackerServer, null);

                String filename = file.getOriginalFilename();
                String extName = StringUtils.substringAfterLast(filename, ".");

                String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
                imgUrl = fileUrl;
                for (int i = 0; i < upload_file.length; i++) {
                    String path = upload_file[i];
                    imgUrl += "/" + path;
                }

            }

            return imgUrl;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return "";
    }

    @RequestMapping("/getOne")
    public EduTeacher getOneTeacher(@RequestParam(value = "userId") String userId) {
        EduTeacher eduTeacher =teacherService.getOne(userId);
        return eduTeacher;
    }

}
