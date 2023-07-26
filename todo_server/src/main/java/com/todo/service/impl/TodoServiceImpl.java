package com.todo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.todo.controller.Result;
import com.todo.entity.Todos;
import com.todo.mapper.TodosMapper;
import com.todo.service.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodosService {

    @Autowired
    private TodosMapper todosMapper;

    @Override
    public Result addTodos(Todos todos) {
        todosMapper.addTodos(todos);
        return Result.ok(null,"新增待办成功(*^▽^*)");
    }


    @Override
    public Result getTodosByPhone(String phone) {
        List<Todos> todos = todosMapper.getTodosByPhone(phone);
        return Result.ok(todos,"成功获取该用户的待办事项");
    }


    @Override
    public Result updateDoneById(Todos todos) {
        todosMapper.updateDoneById(todos);
        return Result.ok(null,"更新成功");
    }


    @Override
    public Result deleteById(Integer id) {
        todosMapper.deleteById(id);
        return Result.ok(null,"删除成功");
    }
}
