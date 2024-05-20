package com.example.backend.DAO;
import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


// This is the mapper class for User entity.

@Mapper
public interface UserDAO {

    @Select("select * from user where name = #{name}")
    User findByName(String name);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(String email);

    @Insert("INSERT INTO user (name, age, gender, phone, password, email) VALUES (#{name}, #{age}, #{gender}, #{phone}, #{password}, #{email})")
    int insertUser(User user);
}
