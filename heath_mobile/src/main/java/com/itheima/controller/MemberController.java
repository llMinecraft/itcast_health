package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/*
* 处理会员操作
* */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;
    //提供手机号快速登录

    @RequestMapping("/login")
    public Result login(HttpServletResponse response ,@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        if(true){
            Member member = memberService.findByTelephone(telephone);
            //判断当前用户是否为vip
            if(member==null){
                //设置注册用户日期
                member.setRegTime(new Date());
                //电话号码
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }
            //写入Cookie发送到客户端
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);

        }
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
