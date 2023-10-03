package com.qiuyu.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiuyu.zhxy.pojo.Grade;
import com.qiuyu.zhxy.utils.Result;

import java.util.List;


/**
 * (Grade)表服务接口
 *
 * @author makejava
 * @since 2023-05-20 00:34:32
 */
public interface GradeService extends IService<Grade> {

    Result getGrades();

    Result getGradess(Integer pageNum, Integer pageSize, String name);

    Result saveOrUpdateGrade(Grade grade);

    Result deleteGrade(List<String> ids);
}

