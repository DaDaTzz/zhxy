package com.qiuyu.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiuyu.zhxy.pojo.Teacher;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/19 22:10
 */
public interface TeacherService extends IService<Teacher> {
    Result getTeachers(Integer pageNum, Integer pageSize, String name, String clazzName);

    Result saveOrUpdateTeacher(Teacher teacher);

    Result deleteTeacher(List<String> ids);
}
