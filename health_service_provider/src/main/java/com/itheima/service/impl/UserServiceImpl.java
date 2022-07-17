package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    //根据用户名查询数据库获取用户信息和关联的角色信息，用时需要查询角色关联的权限信息
    @Override
    public User findByusername(String name) {
        User user = userDao.findByusername(name);
        if(user==null){
            return null;
        }
        Integer userid = user.getId();
        //根据用户ID查找角色ID
        Set<Role> roles = roleDao.findByUserId(userid);
        for (Role role : roles) {
            Integer roleId = role.getId();
            //根据角色ID查询权限ID
            Set<Permission> permissions = permissionDao.findByroleId(roleId);
            if(permissions!=null&&permissions.size()>0){
                role.setPermissions(permissions);
            }
        }
        user.setRoles(roles);

        return user;
    }
}
