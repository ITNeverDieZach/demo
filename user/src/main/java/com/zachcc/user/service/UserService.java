package com.zachcc.user.service;

import com.zachcc.common.common.Result;
import com.zachcc.mbg.entities.User;

/**
 * @author zhangc
 * @date 2021/8/22
 */
public interface UserService {
    public Result login(User user);

    public Result signUp(User user);

    public Result updateUser(User user);

    public Result deleteUser(String uuid);

    public Result getUserInfo(String uuid);
}
