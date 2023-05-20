package com.qiuyu.zhxy.controller;

import com.qiuyu.zhxy.pojo.Admin;
import com.qiuyu.zhxy.service.AdminService;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/20 14:47
 */
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Resource
    private AdminService adminService;

    @GetMapping("/getAllAdmin/{pageNum}/{pageSize}")
    public Result getAllAdmin(@PathVariable Integer pageNum,@PathVariable Integer pageSize,String adminName){
        return adminService.getAllAdmin(pageNum,pageSize,adminName);
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        return adminService.saveOrUpdateAdmin(admin);
    }

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<String> ids){
        return adminService.deleteAdmin(ids);
    }
}
