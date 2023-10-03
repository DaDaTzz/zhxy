package com.qiuyu.zhxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 秋雨
 * @date 2023/5/19 21:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 用户类型
    private Integer userType;
    // 验证码
    private String verifiCode;
}
