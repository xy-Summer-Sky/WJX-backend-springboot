package com.example.backend.service;
import com.example.backend.Entity.User;
public interface UserService {

    public User findById(Integer id);
    public int insertUser(User user);

}
