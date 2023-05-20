package com.qiuyu.zhxy.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.qiuyu.zhxy.pojo.Admin;
import com.qiuyu.zhxy.pojo.LoginInfo;
import com.qiuyu.zhxy.pojo.Student;
import com.qiuyu.zhxy.pojo.Teacher;
import com.qiuyu.zhxy.service.AdminService;
import com.qiuyu.zhxy.service.StudentService;
import com.qiuyu.zhxy.service.TeacherService;
import com.qiuyu.zhxy.utils.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 秋雨
 * @date 2023/5/19 21:36
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;


    /**
     * 获取验证码图片
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码字符串
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());

        /*将验证码放入当前请求域*/
        request.getSession().setAttribute("verifiCode",verifiCode);
        try {
            //将验证码图片通过输出流做出响应
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     * @param request
     * @param loginInfo
     * @return
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody LoginInfo loginInfo){
        if(loginInfo.getUsername() == null || loginInfo.getPassword() == null){
            return Result.fail().message("用户名或密码不能为空");
        }


        // 获取用户提交的验证码
        String verifiCodeFromUser = loginInfo.getVerifiCode().toLowerCase();
        // 获取session中的验证码
        Object verifiCodeInSession = request.getSession().getAttribute("verifiCode").toString().toLowerCase();
        if("".equals(verifiCodeInSession)){
            return Result.fail().message("验证码失效,请刷新后重试");
        }
        if(!verifiCodeFromUser.equals(verifiCodeInSession)){
            return Result.fail().message("验证码错误，请刷新后重试");
        }
        // 移除session中的验证码信息
        request.getSession().removeAttribute("verifiCode");

        // 用户类型
        String userType = loginInfo.getUserType().toString();
        // 用户名
        String username = loginInfo.getUsername();
        // 获取密码，并进行MD5加密
        String password = MD5.encrypt(loginInfo.getPassword());

        HashMap<String, String> map = new HashMap<>();
        // 管理员
        if("1".equals(userType)){
            Admin admin = adminService.lambdaQuery().eq(Admin::getName, username).eq(Admin::getPassword, password).one();
            if(Objects.isNull(admin)){
                return Result.fail().message("用户名或密码错误");
            }
            // 生成token，返回给前端
            String token = JwtHelper.createToken(admin.getId().longValue(), 1);
            map.put("token",token);
            return Result.ok(map);
        }

        //学生
        if("2".equals(userType)){
            Student student = studentService.lambdaQuery().eq(Student::getSno, username).eq(Student::getPassword, password).one();
            if(Objects.isNull(student)){
                return Result.fail().message("用户名或密码错误");
            }
            // 生成token，返回给前端
            String token = JwtHelper.createToken(student.getId().longValue(), 2);
            map.put("token",token);
            return Result.ok(map);
        }

        // 老师
        if("3".equals(userType)){
            Teacher teacher = teacherService.lambdaQuery().eq(Teacher::getTno, username).eq(Teacher::getPassword, password).one();
            if(Objects.isNull(teacher)){
                return Result.fail().message("用户名或密码错误");
            }
            // 生成token，返回给前端
            String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
            map.put("token",token);
            return Result.ok(map);
        }

        return Result.fail().message("登录失败，未知原因！");
    }

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    public Result getUserInfoByToken(@RequestHeader String token){
        boolean expiration = JwtHelper.isExpiration(token);
        // token过期
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }

        // 通过token解析userId
        Long userId = JwtHelper.getUserId(token);
        // 通过token解析userType
        Integer userType = JwtHelper.getUserType(token);

        HashMap<Object, Object> map = new HashMap<>();
        if("1".equals(userType.toString())){
            Admin admin = adminService.getById(userId);
            map.put("user", admin);
            map.put("userType", userType);
            return Result.ok(map);
        }

        if("2".equals(userId.toString())){
            Student student = studentService.getById(userId);
            map.put("user", student);
            map.put("userType", userType);
            return Result.ok(map);
        }

        if("3".equals(userId.toString())){
            Teacher teacher = teacherService.getById(userId);
            map.put("user", teacher);
            map.put("userType", userType);
            return Result.ok(map);
        }
        return Result.fail().message("无效用户！");
    }

    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@PathVariable String oldPwd,@PathVariable String newPwd,@RequestHeader String token){
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        if("1".equals(userType.toString())){
            // 判断原密码是否正确
            Admin admin = adminService.lambdaQuery().eq(Admin::getId,userId).eq(Admin::getPassword, MD5.encrypt(oldPwd)).one();
            if(admin == null){
                return Result.fail().message("原密码错误");
            }
            if(admin.getPassword().equals(newPwd)){
                return Result.fail().message("新密码不能和原来密码相同");
            }
            // 判断新密码是否和原来密码相同
            Admin a = new Admin();
            a.setPassword(MD5.encrypt(newPwd));
            a.setId(Integer.parseInt(userId.toString()));
            adminService.updateById(a);
            return Result.ok().message("密码修改成功");
        }

        if("2".equals(userType.toString())){
            // 判断原密码是否正确
            Student student = studentService.lambdaQuery().eq(Student::getId,userId).eq(Student::getPassword, MD5.encrypt(oldPwd)).one();
            if(student == null){
                return Result.fail().message("原密码错误");
            }
            if(student.getPassword().equals(newPwd)){
                return Result.fail().message("新密码不能和原来密码相同");
            }
            Student s = new Student();
            s.setPassword(MD5.encrypt(newPwd));
            s.setId(Integer.parseInt(userId.toString()));
            studentService.updateById(s);
            return Result.ok().message("密码修改成功");
        }

        if("3".equals(userType.toString())){
            // 判断原密码是否正确
            Teacher teacher = teacherService.lambdaQuery().eq(Teacher::getId,userId).eq(Teacher::getPassword, MD5.encrypt(oldPwd)).one();
            if(teacher == null){
                return Result.fail().message("原密码错误");
            }
            if(teacher.getPassword().equals(newPwd)){
                return Result.fail().message("新密码不能和原来密码相同");
            }
            Teacher t = new Teacher();
            t.setPassword(MD5.encrypt(newPwd));
            t.setId(Integer.parseInt(userId.toString()));
            teacherService.updateById(t);
            return Result.ok().message("密码修改成功");
        }

        return Result.fail().message("密码修改错误，未知原因，请联系管理员！");
    }

}



