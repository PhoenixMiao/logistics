package com.phoenix.logistics.shiro;

import com.phoenix.logistics.dto.UserDTO;
import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.Admin_role;
import com.phoenix.logistics.entity.User;
import com.phoenix.logistics.entity.User_role;
import com.phoenix.logistics.mapper.AdminMapper;
import com.phoenix.logistics.mapper.Admin_roleMapper;
import com.phoenix.logistics.mapper.UserMapper;
import com.phoenix.logistics.mapper.User_roleMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;

    @Autowired
    User_roleMapper user_roleMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    Admin_roleMapper admin_roleMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Object principal = principals.getPrimaryPrincipal();
        UserDTO userDTO = (UserDTO) principal;

        String username = userDTO.getUsername();
        int type = userDTO.getType();

        //注入角色与权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //管理员
        if(type == 1){
//            info.addRole("user");
            info.addRole("admin");
//            Admin_role admin_role = admin_roleMapper.getAdmin_roleByUsername(username);
//            if(admin_role != null && admin_role.getSuperior() == 1){
//                info.addRole("superior");
//            }
        }

        //普通用户
        if(type == 0){
            info.addRole("user");
//            User_role user_role = user_roleMapper.getUser_roleByUsername(username);
//            if(user_role != null && user_role.getCompany() == 1){
//                info.addRole("company");
//            }

        }
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //数据库匹配，认证
        String username = token.getUsername();
        String password = new String(token.getPassword());

        //管理员
        Admin admin = adminMapper.getAdminByUsername(username);
        if(admin != null && (admin.getPassword()+"").equals(password)){
            UserDTO userDTO = new UserDTO(admin);

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDTO, token.getCredentials(), getName());
            return info;
        }

        //普通用户
        User user = userMapper.getUserByUsername(username);
        if(user != null && (user.getPassword()+"").equals(password)){
            UserDTO userDTO = new UserDTO(user);

            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDTO, token.getCredentials(), getName());
            return info;
        }

        //认证失败
        throw new AuthenticationException();
    }
}
