package com.todo.controller;

import com.todo.entity.User;
import com.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser(HttpSession session, HttpServletRequest request) {
        String phone = request.getHeader("token");
        // 检查用户是否已登录
        Object user = session.getAttribute(phone);
        if (user == null) {
            // 用户未登录，返回错误信息
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // 用户已登录，返回用户信息
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        return userService.login(user.getPhone(), user.getPassword(), session);
    }

    @PutMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/check")
    public Result checkPhone(@RequestBody String phone) {
        return userService.checkPhone(phone);
    }

    @GetMapping("/{phone}")
    public Result getUserNameByPhone(@PathVariable String phone) {
        return userService.getUserNameByPhone(phone);
    }


}
