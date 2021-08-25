package com.zachcc.user.controller;

import com.zachcc.common.common.Result;
import com.zachcc.mbg.entities.User;
import com.zachcc.shiro.annotation.JwtIgnore;
import com.zachcc.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangc
 * @date 2021/8/22
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation("登录")
    @JwtIgnore
    public Result login(@RequestBody User user) {
//        String token;
//        String secret = MD5Utils.md5Encryption(user.getPassword(),user.getName());
//        token = JWTUtils.sign(user.getName(),secret);
//        SecurityUtils.getSubject().login(new JWTToken(token));
//        HashMap<String,String> result = new HashMap<>();
//        result.put("token",token);
        return userService.login(user);
    }


    @PostMapping("/signUp")
    @JwtIgnore
    public Result signUp(@RequestBody User user) {
        return userService.signUp(user);
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateUser(user);
    }


    @GetMapping("/userInfo")
    public Result getUserInfo(@RequestParam(value = "uuid", defaultValue = "") String uuid) {
        return userService.getUserInfo(uuid);
    }

    @GetMapping("/deleteUser")
    public Result deleteUser(@RequestParam(value = "uuid", defaultValue = "") String uuid) {
        return userService.deleteUser(uuid);
    }
}
