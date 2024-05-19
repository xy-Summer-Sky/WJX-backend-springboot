package com.example.backend.mapper;
import com.example.backend.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    public User getUserById(Integer id);
}
