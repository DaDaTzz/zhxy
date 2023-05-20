package com.qiuyu.zhxy.controller;

import com.qiuyu.zhxy.pojo.Grade;
import com.qiuyu.zhxy.service.GradeService;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/20 0:33
 */
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 年级列表
     * @return
     */
    @GetMapping("/getGrades")
    public Result getGrades(){
        return gradeService.getGrades();
    }

    @GetMapping("/getGrades/{pageNum}/{pageSize}")
    public Result getGradess(@PathVariable Integer pageNum,@PathVariable Integer pageSize,String name){
        return gradeService.getGradess(pageNum,pageSize,name);
    }

    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        return gradeService.saveOrUpdateGrade(grade);
    }

    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<String> ids){
        return gradeService.deleteGrade(ids);
    }
}
