package com.qiuyu.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiuyu.zhxy.mapper.ClazzMapper;
import com.qiuyu.zhxy.pojo.Clazz;
import com.qiuyu.zhxy.pojo.Teacher;
import com.qiuyu.zhxy.service.ClazzService;

import com.qiuyu.zhxy.service.TeacherService;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * (Clazz)表服务实现类
 *
 * @author makejava
 * @since 2023-05-20 00:06:29
 */
@Service("clazzService")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Resource
    private TeacherService teacherService;

    @Override
    public Result getClazzList() {
        List<Clazz> clazzes = list();
        return Result.ok(clazzes);
    }

    @Override
    public Result saveOrUpdateClazz(Clazz clazz) {
        removeById(clazz.getId());
        Clazz c = lambdaQuery().eq(Clazz::getName, clazz.getName()).one();
        if(c != null){
            return Result.fail().message("班级已存在");
        }
        // 判断老师是否带了其他的班
        Clazz cc = lambdaQuery().eq(Clazz::getHeadmaster, clazz.getHeadmaster()).one();
        if(cc != null){
            return Result.fail().message("一位老师只能带一个班级");
        }
        // 判断是否存在该老师
        Teacher teacher = teacherService.lambdaQuery().eq(Teacher::getName, clazz.getHeadmaster()).one();
        if(teacher == null){
            return Result.fail().message("老师不存在");
        }
        // 判断班级名称格式是否正确
        String regex = "(一|二|三|四|五|六)年(一|二|三|四|五|六|七|八|九|十|十一|十二|十三|十四|十五|十六|十七|十八|十九|二十)班";
        String a = clazz.getGradeName().substring(0, 1);
        String b = clazz.getName().substring(0, 1);
        if(!Pattern.matches(regex, clazz.getName()) || !a.equals(b)){
            return Result.fail().message("班级名称格式不正确");
        }
        save(clazz);
        return Result.ok().message("操作成功");
    }

    @Override
    public Result deleteClazz(List<String> ids) {
        removeByIds(ids);
        return Result.ok().message("删除成功");
    }

    @Override
    public Result getClazzsByOpr(Integer pageNum, Integer pageSize, String name, String gradeName) {
        Page<Clazz> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(name != null){
            clazzLambdaQueryWrapper.eq(Clazz::getName,name);
        }
        if(gradeName != null){
            clazzLambdaQueryWrapper.eq(Clazz::getGradeName,gradeName);
        }
        clazzLambdaQueryWrapper.orderByAsc(Clazz::getId);
        Page<Clazz> clazzPage = this.page(pageInfo, clazzLambdaQueryWrapper);
        return Result.ok(clazzPage);
    }
}

