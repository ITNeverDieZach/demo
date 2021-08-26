package com.zachcc.user.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zachcc.common.common.Result;
import com.zachcc.common.common.ResultCode;
import com.zachcc.common.utils.JWTUtils;
import com.zachcc.mbg.dao.UserDao;
import com.zachcc.mbg.entities.User;
import com.zachcc.shiro.config.shiro.JWTToken;
import com.zachcc.shiro.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author zhangc
 * @date 2021/8/22
 */
@Service
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public Result login(User user) {
        JSONObject result = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        String target = MD5Utils.md5Encryption(user.getPassword(), user.getName());
        String token = JWTUtils.sign(user.getName(), target);
        try {
            subject.login(new JWTToken(token));
            // 登录成功 封装用户信息到token
            result.put("token", token);
            return Result.SUCCESS(result);
        } catch (UnknownAccountException e) {
            return new Result(ResultCode.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            return new Result(ResultCode.USER_LOGIN_ERROR);
        }
    }

    @Override
    public Result signUp(User user) {
        if (user.getName().equals("") || user.getPassword().equals("")) {
            return new Result(ResultCode.PARAM_IS_INVALID);
        }
        if (user.getName() == null || user.getPassword().equals("")) {
            return new Result(ResultCode.PARAM_IS_INVALID);
        }
        User user1 = userDao.selectByName(user.getName());
        if (user1 == null) {
            user.setUuid(UUID.randomUUID().toString());
            userDao.insertSelective(user);
            return Result.SUCCESS();
        }
        return Result.FAIL("用户已存在");
    }

    @Override
    public Result updateUser(User user) {
//        userDao.updateByUUIDSelective(user);
        return Result.SUCCESS();
    }

    @Override
    public Result deleteUser(String uuid) {
        return Result.SUCCESS(userDao.deleteByUUID(uuid));
    }

    @Override
    public Result getUserInfo(String uuid) {
        log.info(uuid);
        if (uuid.equals("")) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            user.setPassword("");
            return Result.SUCCESS(user);
        } else {
            return Result.SUCCESS(userDao.selectByUUID(uuid));
        }
    }

    @Override
    public Result selectAllUsers(int start, int size) {
        PageHelper.startPage(start,size);
        List<User> userList = userDao.selectUsers();
        PageInfo<User> pageInfo = new PageInfo<>(userList,5);
        return Result.SUCCESS(pageInfo);

    }
}
