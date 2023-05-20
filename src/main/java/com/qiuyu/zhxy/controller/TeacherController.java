package com.qiuyu.zhxy.controller;

import com.qiuyu.zhxy.pojo.Teacher;
import com.qiuyu.zhxy.service.TeacherService;
import com.qiuyu.zhxy.utils.Result;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/20 12:22
 */
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    /**
     * 老师信息
     * @param pageNum
     * @param pageSize
     * @param name
     * @param clazzName
     * @return
     */
    @GetMapping("/getTeachers/{pageNum}/{pageSize}")
    public Result getTeachers(@PathVariable Integer pageNum, @PathVariable Integer pageSize,String name,String clazzName){
        return teacherService.getTeachers(pageNum,pageSize,name,clazzName);
    }

    @PostMapping("saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        return teacherService.saveOrUpdateTeacher(teacher);
    }

    /**
     * 老师删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(List<String> ids){
        return teacherService.deleteTeacher(ids);
    }
}
