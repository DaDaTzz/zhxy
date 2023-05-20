package com.qiuyu.zhxy;

import com.qiuyu.zhxy.pojo.Student;
import com.qiuyu.zhxy.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class ZhxyApplicationTests {
    @Resource
    private StudentService studentService;

    @Test
    void contextLoads() {
        String  regex  =  "(一|二|三|四|五|六)年(一|二|三|四|五|六|七|八|九|十|十一|十二|十三|十四|十五|十六|十七|十八|十九|二十)班";
        String s = "一年一班";
        if(Pattern.matches(regex, s)){
            System.out.println("111");
        }
    }

}
