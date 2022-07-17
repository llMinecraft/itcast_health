package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.api.R;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/getUsername")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        if(user!=null){
            String name = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,name);
        }
        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }
}
