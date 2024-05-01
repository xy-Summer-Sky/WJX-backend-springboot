package com.example.backend.service.impl;
import com.example.backend.service.UserService;
import com.example.backend.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.getUserById(id);
    }
}
