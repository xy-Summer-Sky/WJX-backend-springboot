package com.example.backend.mapper;
import com.example.backend.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


// This is the mapper class for User entity.

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getUserById(Integer id);

    @Insert("insert into user (name, age, gender, phone, password) VALUES (#{name}, #{age}, #{gender}, #{phone}, #{password})")
    int insertUser(User user);
}
