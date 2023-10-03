package com.qiuyu.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiuyu.zhxy.mapper.AdminMapper;
import com.qiuyu.zhxy.pojo.Admin;
import com.qiuyu.zhxy.service.AdminService;
import com.qiuyu.zhxy.utils.MD5;
import com.qiuyu.zhxy.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/19 22:12
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService{

    @Override
    public Result getAllAdmin(Integer pageNum, Integer pageSize, String adminName) {
        Page<Admin> pageInfo = new Page<>();
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(adminName != null){
            adminLambdaQueryWrapper.like(Admin::getName,adminName);
        }
        adminLambdaQueryWrapper.orderByAsc(Admin::getId);
        Page<Admin> adminPage = this.page(pageInfo, adminLambdaQueryWrapper);
        return Result.ok(adminPage);
    }

    @Override
    public Result saveOrUpdateAdmin(Admin admin) {
        removeById(admin.getId());
        // 判断name是否存在
        if(lambdaQuery().eq(Admin::getName,admin.getName()).one() != null){
            return Result.fail().message("管理员已存在");
        }
        // 密码加密
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        save(admin);
        return Result.ok().message("操作成功");
    }

    @Override
    public Result deleteAdmin(List<String> ids) {
        removeByIds(ids);
        return Result.ok().message("删除成功");
    }



}
