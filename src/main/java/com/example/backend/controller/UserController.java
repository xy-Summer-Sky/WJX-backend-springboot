package com.example.backend.controller;
import com.example.backend.Entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// 定义了UserController类，用于处理用户相关的请求。
// 该类使用@RestController注解，表示它是一个控制器类，使用@Autowired注解注入了UserService类，用于处理业务逻辑。
// 该类提供了两个请求处理方法：findById和save。findById方法用于根据用户ID查询用户信息，save方法用于保存用户信息。
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }

     @RequestMapping("/save")
    public int save(User user) {
        return userService.insertUser(user);
    }

}
