package com.qiuyu.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiuyu.zhxy.pojo.Student;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/19 22:07
 */

public interface StudentService extends IService<Student> {
    Result getStudentByOpr(Integer pageNum, Integer pageSize, String name,String clazzName);

    Result addOrUpdateStudent(Student student);

    Result delStudentById(List<String> ids);
}
