package com.zachcc.shiro.config.shiro;


import com.zachcc.common.common.ResultCode;
import com.zachcc.mbg.dao.UserDao;
import com.zachcc.mbg.dao.UserRoleDao;
import com.zachcc.mbg.entities.User;
import com.zachcc.mbg.entities.UserRole;
import com.zachcc.shiro.exception.CustomException;
import com.zachcc.common.utils.JWTUtils;
import com.zachcc.shiro.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {
    // 授权
    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleDao userRoleDao;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行授权");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if (user.getPermissions() != null) {
            for (String per : user.getPermissions()) {
                authorizationInfo.addStringPermission(per);
            }
        }
        if (user.getRoles() != null) {
            user.getRoles().forEach(authorizationInfo::addRole);
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userToken = (String) authenticationToken.getCredentials();
        String username = JWTUtils.getUsername(userToken);
        if (username == null) {
            throw new AuthenticationException(new CustomException(ResultCode.PARAM_NOT_COMPLETE));
        }
        User userBean = userDao.selectByName(username);

        if (userBean == null) {
            throw new AuthenticationException(new CustomException(ResultCode.USER_NOT_EXIST));
        }

        if (JWTUtils.isExpire(userToken)) {
            throw new AuthenticationException(new CustomException(ResultCode.PERMISSION_EXPIRE));
        }
        String target = MD5Utils.md5Encryption(userBean.getPassword(), userBean.getName());

        if (!JWTUtils.verify(userToken, username, target)) {
            throw new AuthenticationException(new CustomException(ResultCode.USER_LOGIN_ERROR));
        }

        User user_permission = userDao.getUserPermission(userBean.getName());
        List<UserRole> roles = userRoleDao.selectByPrimaryKey(userBean.getUuid());
        if (user_permission != null) {
            if (!user_permission.getPermissions().isEmpty()) {
                userBean.setPermissions(user_permission.getPermissions());
            }
        }
        List<String> r = new ArrayList<>();
        if (roles != null) {
            roles.forEach((UserRole u) -> {
                r.add(u.getRole());
            });
            userBean.setRoles(r);
        }
        return new SimpleAuthenticationInfo(userBean, userToken, getName());
    }
}
