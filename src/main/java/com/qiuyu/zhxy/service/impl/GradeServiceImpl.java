package com.qiuyu.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiuyu.zhxy.mapper.GradeMapper;
import com.qiuyu.zhxy.pojo.Grade;
import com.qiuyu.zhxy.pojo.Teacher;
import com.qiuyu.zhxy.service.GradeService;
import com.qiuyu.zhxy.service.TeacherService;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Grade)表服务实现类
 *
 * @author makejava
 * @since 2023-05-20 00:34:32
 */
@Service("gradeService")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Resource
    private TeacherService teacherService;

    @Override
    public Result getGrades() {
        List<Grade> grades = list();
        return Result.ok(grades);
    }

    @Override
    public Result getGradess(Integer pageNum, Integer pageSize, String name) {
        Page<Grade> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Grade> gradeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(name != null){
            gradeLambdaQueryWrapper.like(Grade::getName,name);
        }
        gradeLambdaQueryWrapper.orderByAsc(Grade::getId);
        Page<Grade> gradePage = this.page(pageInfo, gradeLambdaQueryWrapper);
        return Result.ok(gradePage);
    }

    @Override
    public Result saveOrUpdateGrade(Grade grade) {
            // 先删除
            removeById(grade.getId());
            Grade g = lambdaQuery().eq(Grade::getName, grade.getName()).one();
            // 判断年级是否存在
            if(g != null){
                return Result.fail().message("年级已经存在");
            }
            // 判断老师名字是否存在
            Teacher teacher = teacherService.lambdaQuery().eq(Teacher::getName, grade.getManager()).one();
            if(teacher == null){
                return Result.fail().message("老师信息不存在");
            }
            // 判断老师是否已经是年级主任
            Grade gg = lambdaQuery().eq(Grade::getManager, grade.getManager()).one();
            if(gg != null){
                return Result.fail().message("一位老师只能管理一个年级");
            }
            save(grade);
            return Result.ok().message("操作成功");

    }

    @Override
    public Result deleteGrade(List<String> ids) {
        removeByIds(ids);
        return Result.ok().message("删除成功");
    }
}

