package com.example.backend.service;
import com.example.backend.DTO.UserDTO;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DAO.UserDAO;
import com.example.backend.config.SecurityConfig;
@Service
public class UserService {

    private final UserDAO userDAO;
    private final SecurityConfig securityConfig;
    @Autowired
    public UserService(UserDAO userDAO, SecurityConfig securityConfig) {
        this.userDAO = userDAO;
        this.securityConfig = securityConfig;
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
        user.setPassword(securityConfig.encodePassword(userDTO.getPassword()));
        userDAO.insertUser(user);
        return "User registered successfully.";
    }


}
