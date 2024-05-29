package com.example.backend.service;
import com.example.backend.DTO.UserDto;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.serviceInterface.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.config.SecurityConfig;
@Service
public class UserServiceImpl implements UserServiceInter {

    private final UserMapper userDAO;
    private final SecurityConfig securityConfig;
    @Autowired
    public UserServiceImpl(UserMapper userDAO, SecurityConfig securityConfig) {
        this.userDAO = userDAO;
        this.securityConfig = securityConfig;
    }

    @Override
    public String registerUser(UserDto userDTO) {
        User existingUser = userDAO.findByEmail(userDTO.getEmail());
        if (existingUser!= null) {
            return "User with email " + userDTO.getEmail() + " already exists.";
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setId(userDTO.getId());

        // 密码加密
        user.setPassword(securityConfig.encodePassword(userDTO.getPassword()));
        userDAO.insertUser(user);
        return "User registered successfully.";
    }


    public Integer getUserId(String email) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public UserDto getUserById(Integer id) {
        User user = userDAO.selectByPrimaryKey(id);
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getGender(), user.getPhone(), user.getPassword(), user.getEmail());
    }
}
