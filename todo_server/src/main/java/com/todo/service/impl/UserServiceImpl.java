package com.todo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.todo.controller.Result;
import com.todo.entity.User;
import com.todo.mapper.UserMapper;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(String phone, String password, HttpSession session) {

        String phoneAfterMd5Hex = DigestUtil.md5Hex(phone);
        String passwordAfterMd5Hex = DigestUtil.md5Hex(password);

        User hasRegister = userMapper.hasRegister(phoneAfterMd5Hex);
        if (BeanUtil.isEmpty(hasRegister)) return Result.fail("您还未注册,快去注册吧~");


        User user = userMapper.login(phoneAfterMd5Hex, passwordAfterMd5Hex);
        if (BeanUtil.isEmpty(user)) return Result.fail("密码错误了哦~");

        String uuid = IdUtil.simpleUUID();
        session.setAttribute(phoneAfterMd5Hex, uuid);
        session.setMaxInactiveInterval(1800);

        return Result.ok(phoneAfterMd5Hex, "登陆成功");
    }

    @Override
    public Result register(User user) {
        String phoneAfterMd5Hex = DigestUtil.md5Hex(user.getPhone());
        String passwordAfterMd5Hex = DigestUtil.md5Hex(user.getPassword());
        synchronized (this) {

            User hasRegister = userMapper.hasRegister(phoneAfterMd5Hex);
            if (BeanUtil.isNotEmpty(hasRegister))
                return Result.fail("该手机号已被注册,请更换一个吧");

            user.setPhone(phoneAfterMd5Hex);
            user.setPassword(passwordAfterMd5Hex);
            userMapper.register(user);
        }
        return Result.ok(null, "注册成功");
    }

    @Override
    public Result checkPhone(String phone) {
        User user = userMapper.hasRegister(phone);
        if (BeanUtil.isEmpty(user)) {
            return Result.ok(null, "该手机号可注册");
        }
        return Result.fail("该手机号已被占用");
    }

    @Override
    public Result getUserNameByPhone(String phone) {
        String name = userMapper.getUserNameByPhone(phone);
        if (StrUtil.isBlank(name)) {
            return Result.fail("请先登陆哦~");
        }
        return Result.ok(name, "根据手机号获取用户名成功");
    }
}
