package com.todo.service;

import com.todo.controller.Result;
import com.todo.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Transactional
public interface UserService {
    Result login(String phone, String password, HttpSession session);

    Result register(User user);

    Result checkPhone(String phone);

    Result getUserNameByPhone(String phone);
}
