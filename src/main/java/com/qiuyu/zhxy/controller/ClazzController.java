package com.qiuyu.zhxy.controller;

import com.qiuyu.zhxy.pojo.Clazz;
import com.qiuyu.zhxy.service.ClazzService;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Clazz)表控制层
 *
 * @author makejava
 * @since 2023-05-20 00:06:28
 */
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    /**
     * 服务对象
     */
    @Resource
    private ClazzService clazzService;

    /**
     * 班级列表
     * @return
     */
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        return clazzService.getClazzList();
    }


    /**
     * 班级列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param gradeName
     * @return
     */
    @GetMapping("/getClazzsByOpr/{pageNum}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable Integer pageNum,@PathVariable Integer pageSize,String name,String gradeName){
        return clazzService.getClazzsByOpr(pageNum,pageSize,name,gradeName);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        return clazzService.saveOrUpdateClazz(clazz);
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<String> ids){
        return clazzService.deleteClazz(ids);
    }
}

