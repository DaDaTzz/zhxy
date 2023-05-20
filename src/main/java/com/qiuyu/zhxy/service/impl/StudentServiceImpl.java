package com.qiuyu.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiuyu.zhxy.mapper.StudentMapper;
import com.qiuyu.zhxy.pojo.Student;
import com.qiuyu.zhxy.service.StudentService;
import com.qiuyu.zhxy.utils.MD5;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/19 22:09
 */
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService{

    @Override
    public Result getStudentByOpr(Integer pageNum, Integer pageSize, String name,String clazzName) {
        Page<Student> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            studentLambdaQueryWrapper.like(Student::getName,name);
        }
        if(!StringUtils.isEmpty(clazzName)){
            studentLambdaQueryWrapper.like(Student::getClazzName,clazzName);
        }
        studentLambdaQueryWrapper.orderByAsc(Student::getSno);
        Page<Student> studentPage = page(pageInfo, studentLambdaQueryWrapper);
        return Result.ok(studentPage);
    }

    @Override
    public Result addOrUpdateStudent(Student student) {
        Integer studentId = student.getId();
        // 前端没有传过来id，add
        if(studentId == null){
            // 判断学号是否存在
            Student s = lambdaQuery().eq(Student::getSno, student.getSno()).one();
            if(s != null){
                return Result.fail().message("学号已存在");
            }
            // 密码加密
            student.setPassword(MD5.encrypt(student.getPassword()));
            save(student);
            return Result.ok().message("新增学生信息成功");
        }
        // 传过来id，update
        student.setPassword(MD5.encrypt(student.getPassword()));
        updateById(student);
        return Result.ok().message("学生信息修改成功");
    }

    @Override
    public Result delStudentById(List<String> ids) {
        removeByIds(ids);
        return Result.ok().message("删除成功！");
    }
}
