package com.qiuyu.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiuyu.zhxy.pojo.Clazz;
import com.qiuyu.zhxy.utils.Result;

import java.util.List;


/**
 * (Clazz)表服务接口
 *
 * @author makejava
 * @since 2023-05-20 00:06:29
 */
public interface ClazzService extends IService<Clazz> {

    Result getClazzList();

    Result getClazzsByOpr(Integer pageNum, Integer pageSize, String name, String gradeName);

    Result saveOrUpdateClazz(Clazz clazz);

    Result deleteClazz(List<String> ids);
}

