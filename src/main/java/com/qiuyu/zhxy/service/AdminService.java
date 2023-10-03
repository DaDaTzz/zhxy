package com.qiuyu.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiuyu.zhxy.pojo.Admin;
import com.qiuyu.zhxy.utils.Result;

import java.util.List;

/**
 * @author 秋雨
 * @date 2023/5/19 22:11
 */
public interface AdminService extends IService<Admin> {
    Result getAllAdmin(Integer pageNum, Integer pageSize, String adminName);

    Result saveOrUpdateAdmin(Admin admin);

    Result deleteAdmin(List<String> ids);
}
