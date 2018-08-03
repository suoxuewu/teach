package com.teach.service.mapper;

import com.teach.bean.EduUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EduUserMapper extends Mapper<EduUser> {

    void batchSave(List<EduUser> list);
}
