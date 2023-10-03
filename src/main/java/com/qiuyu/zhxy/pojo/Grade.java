package com.qiuyu.zhxy.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * (Grade)表实体类
 *
 * @author makejava
 * @since 2023-05-20 00:34:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_grade")
public class Grade  {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;


    private String manager;

    private String email;

    private String telephone;

    private String introducation;



}

