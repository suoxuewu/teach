package com.teach.student.controller;

import com.github.pagehelper.PageInfo;
import com.teach.bean.EduUser;
import com.teach.student.controller.service.UserService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Controller
public class StudentController {

    @Autowired
    private UserService userService;


    @RequestMapping("/index")
    public String index(Map<String, Object> result, @RequestParam(value = "pageNO", defaultValue = "1") Integer pageNo) {

        PageInfo<EduUser> pageInfo = userService.getListUser(pageNo);
        result.put("pageInfo", pageInfo);

        Map<String, String> selectMap = new HashMap<>();
        selectMap.put("1", "正常");
        selectMap.put("0", "冻结");
        result.put("selectMap", selectMap);

        return "index";
    }

    @RequestMapping("/condition")
    public String getStudent(@RequestParam Map<String, Object> paramMap, Map<String, Object> result) {
        //查询条件  -- pageNO

        String condition = (String) paramMap.get("condition");
        String status = (String) paramMap.get("status");
        String createTime = (String) paramMap.get("createTime");
        String endTime = (String) paramMap.get("endTime");

        String queryUrl = "condition=" + condition + "&status=" + status + "&createTime=" + createTime + "&endTime=" + endTime;
        result.put("queryUrl", queryUrl);

        PageInfo<EduUser> pageInfo = userService.getListByCondition(paramMap);
        result.put("pageInfo", pageInfo);

        Map<String, String> selectMap = new HashMap<>();
        selectMap.put("1", "正常");
        selectMap.put("0", "冻结");
        result.put("selectMap", selectMap);


        return "condition";
    }

    @RequestMapping("/release")
    @ResponseBody
    public String changeState(@RequestParam(value = "userId") String userId) {

        String flag = userService.changeState(userId);
        return flag;
    }

    @RequestMapping("/modifyPwd")
    @ResponseBody
    public String changePwd(@RequestParam("userId") String userId, @RequestParam("pwd") String pwd) {
        String result = userService.changePwd(userId, pwd);
        return result;
    }

    @RequestMapping("/exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response) {
        String[] headers = {"学员ID", "手机号", "邮箱号", "密码", "用户名", "显示名(昵称)", "性别(1男2女)", "年龄", "注册时间", "是否可用(1正常2冻结)",
                "用户头像", "个人中心用户背景图片", "站内信未读消息数", "系统消息数", "上传统计系统消息时间"};

        List<EduUser> list = userService.getAll();

        ExportExcel<EduUser> excel = new ExportExcel<>();
        excel.exportExcel(headers, list, "学员信息", response);

    }

    @RequestMapping("/excel")
    public String excel(HttpServletResponse response) {
        return "upload";
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    public String importExcel(MultipartFile file) {
        ExportExcel<EduUser> excel = new ExportExcel<>();
        InputStream is = null;
        try {
            is = file.getInputStream();

            Object[][] array = excel.loadScoreInfo(is);

            List<EduUser> list = new ArrayList<>();


            for (int i = 0; i < array.length; i++) {

                EduUser eduUser = new EduUser();
                eduUser.setMobile(array[i][1].toString());
                eduUser.setEmail(array[i][2].toString());
                eduUser.setPassword(array[i][3].toString());
                eduUser.setUserName(array[i][4].toString());
                eduUser.setShowName(array[i][5].toString());
                eduUser.setSex(Boolean.parseBoolean(array[i][6].toString()));
                eduUser.setAge(Byte.parseByte(array[i][7].toString()));
                eduUser.setCreateTime(new Date(Date.parse(array[i][8].toString())));
                eduUser.setIsAvalible(Boolean.parseBoolean(array[i][9].toString()));
                eduUser.setPicImg(array[i][10].toString());
                eduUser.setBannerUrl(array[i][11].toString());
                eduUser.setMsgNum(Integer.parseInt(array[i][12].toString()));
                eduUser.setSysMsgNum(Integer.parseInt(array[i][13].toString()));
                eduUser.setLastSystemTime(array[i][14].toString());

                list.add(eduUser);
            }
            //保存到数据库
            userService.batchSave(list);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }




    class ExportExcel<T> {

        /**
         * @param headers  excel 表格的标题
         * @param dataset  数据集合
         * @param fileName 导出excel的名字
         * @param response
         */
        public void exportExcel(String[] headers, Collection<T> dataset, String fileName, HttpServletResponse response) {
            // 声明一个工作薄
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 生成一个表格
            XSSFSheet sheet = workbook.createSheet(fileName);

            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(15);
            // 产生表格标题行
            XSSFRow title = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {

                XSSFCell cell = title.createCell(i);
                XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }


            try {
                // 遍历集合数据，产生数据行
                Iterator<T> it = dataset.iterator();
                int index = 0;
                while (it.hasNext()) {
                    index++;

                    XSSFRow row = sheet.createRow(index);

                    T t = (T) it.next();
                    Class<?> clazz = t.getClass();

                    // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                    Field[] fields = clazz.getDeclaredFields();

                    for (int i = 0; i < headers.length; i++) {
                        XSSFCell cell = row.createCell(i);

                        Field field = fields[i];
                        String fieldName = field.getName();
                        //拼接getter
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                        Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
                        Object value = getMethod.invoke(t, new Object[]{});

                        if (value == null) {
                            value = "";
                        }

                        XSSFRichTextString richString = new XSSFRichTextString(value.toString());
                        cell.setCellValue(richString);

                    }
                }

                //导出Excel
                getExportedFile(workbook, fileName, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 指定路径下生成EXCEL文件
         *
         * @return
         */
        public void getExportedFile(XSSFWorkbook workbook, String name, HttpServletResponse response) throws Exception {
            BufferedOutputStream fos = null;
            try {
                String fileName = name + ".xlsx";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                fos = new BufferedOutputStream(response.getOutputStream());
                workbook.write(fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }


        public Object[][] loadScoreInfo(InputStream is) {
            Object[][] array = null;
            try {
                //导入已存在的Excel文件，获得只读的工作薄对象
//                Workbook wk = WorkbookFactory.create(is);
//                HSSFWorkbook wk = new HSSFWorkbook(is);
                XSSFWorkbook wk = new XSSFWorkbook(is);
                //获取第一张Sheet表
                XSSFSheet sheet = wk.getSheetAt(0);
                //获取总行数
                int rowNum = sheet.getLastRowNum();
                //总列数
                int columns = sheet.getRow(0).getLastCellNum();
                //从数据行开始迭代每一行
                array = new Object[rowNum][columns];

                for (int i = 1; i <= rowNum; i++) {

                    for (int j = 0; j < columns; j++) {
                        String text = sheet.getRow(i).getCell(j).getRichStringCellValue().toString();
                        array[i - 1][j] = text;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return array;
        }
    }
}


