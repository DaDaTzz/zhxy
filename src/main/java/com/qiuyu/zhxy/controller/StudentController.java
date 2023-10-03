package com.qiuyu.zhxy.controller;

import com.qiuyu.zhxy.pojo.Student;
import com.qiuyu.zhxy.service.StudentService;
import com.qiuyu.zhxy.utils.Result;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/20 0:16
 */
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 学生列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param clazzName
     * @return
     */
    @GetMapping("/getStudentByOpr/{pageNum}/{pageSize}")
    public Result getStudentByOpr(@PathVariable Integer pageNum,@PathVariable Integer pageSize, String name,String clazzName){
        return studentService.getStudentByOpr(pageNum,pageSize,name,clazzName);
    }

    /**
     * 添加or修改学生信息
     * @param student
     * @return
     */
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        return studentService.addOrUpdateStudent(student);
    }

    /**
     * 删除学生
     * @param ids
     * @return
     */
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<String> ids){
        return studentService.delStudentById(ids);
    }
}
