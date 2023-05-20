package com.qiuyu.zhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.qiuyu.zhxy.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 秋雨
 * @date 2023/5/19 22:08
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
