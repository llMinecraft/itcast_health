package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sun.util.calendar.Gregorian;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;



//这是一个框架
@Component
public class SpringSecurityUserService implements UserDetailsService {
    //使用dubbo通过网络远程调用服务提供方获取数据库中的信息
    @Reference
    private UserService userService;
    //根据用户名查询数据库获取用户数据
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByusername(username);
        //判断用户是否存在
        if(user==null){
            return null;
        }
        //如果存在就进行赋权限赋角色

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        Set<Role> roles  = user.getRoles();
        //进行循环遍历赋值角色权限等
        for (Role role : roles) {
            //赋予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            //获取每个角色都有哪些权限
            Set<Permission> permissions = role.getPermissions();
            //赋予权限
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        //username用户名 user.getPassword密码，list就是认证的角色与权限
        org.springframework.security.core.userdetails.User user1 =
                new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return user1;
    }
}
