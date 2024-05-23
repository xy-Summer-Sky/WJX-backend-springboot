package com.example.backend.service;
import com.example.backend.DTO.UserDTO;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DAO.UserDAO;
import com.example.backend.config.passwordSecurityConfig;
// User Service
// This class is responsible for handling user related operations such as registration, login, logout, etc.
@Service
public class UserService {

    private final UserDAO userDAO;
    private final passwordSecurityConfig passwordSecurityConfig;
    @Autowired
    public UserService(UserDAO userDAO, passwordSecurityConfig passwordSecurityConfig) {
        this.userDAO = userDAO;
        this.passwordSecurityConfig = passwordSecurityConfig;
    }

    public String registerUser(UserDTO userDTO) {
        User existingUser = userDAO.findByEmail(userDTO.getEmail());
        if (existingUser!= null) {
            return "User with email " + userDTO.getEmail() + " already exists.";
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        // 密码加密
        user.setPassword(passwordSecurityConfig.encodePassword(userDTO.getPassword()));
        userDAO.insertUser(user);
        return "User registered successfully.";
    }




    public User findByEmail(String email){
        return userDAO.findByEmail(email);
    }

}
