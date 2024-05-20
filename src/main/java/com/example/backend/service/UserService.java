package com.example.backend.service;
import com.example.backend.DTO.UserDTO;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.DAO.UserDAO;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
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
        user.setPassword(userDTO.getPassword());
        userDAO.insertUser(user);
        return "User registered successfully.";
    }


}
