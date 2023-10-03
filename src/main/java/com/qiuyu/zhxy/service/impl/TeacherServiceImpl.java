package com.qiuyu.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiuyu.zhxy.mapper.TeacherMapper;
import com.qiuyu.zhxy.pojo.Student;
import com.qiuyu.zhxy.pojo.Teacher;
import com.qiuyu.zhxy.service.TeacherService;
import com.qiuyu.zhxy.utils.MD5;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/19 22:10
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Result deleteTeacher(List<String> ids) {
        removeByIds(ids);
        return Result.ok().message("删除成功");
    }

    @Override
    public Result saveOrUpdateTeacher(Teacher teacher) {
        Integer teacherId = teacher.getId();
        // 前端没有传过来id，add
        if(teacherId == null){
            // 判断老师工号是否存在
            Teacher t = lambdaQuery().eq(Teacher::getTno, teacherId).one();
            if(t != null){
                return Result.fail().message("工号已存在");
            }
            // 密码加密
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
            save(teacher);
            return Result.ok().message("新增老师信息成功");
        }
        // 传过来id，update
        teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        updateById(teacher);
        return Result.ok().message("老师信息修改成功");
    }

    @Override
    public Result getTeachers(Integer pageNum, Integer pageSize, String name, String clazzName) {
        Page<Teacher> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(name != null){
            teacherLambdaQueryWrapper.like(Teacher::getName,name);
        }
        if(clazzName != null){
            teacherLambdaQueryWrapper.eq(Teacher::getClazzName,clazzName);
        }
        teacherLambdaQueryWrapper.orderByAsc(Teacher::getTno);
        Page<Teacher> teacherPage = this.page(pageInfo, teacherLambdaQueryWrapper);
        return Result.ok(teacherPage);
    }
}
