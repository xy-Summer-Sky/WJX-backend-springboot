package com.example.backend.mapper;
import org.apache.ibatis.annotations.*;
import com.example.backend.Entity.Survey;
import java.util.List;
public interface SurveyMapper {

    @Select("SELECT * FROM surveys")
    List<Survey> findAll();

    @Insert("INSERT INTO surveys(title, description) VALUES(#{title}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Survey survey);

    @Update("UPDATE surveys SET title = #{title}, description = #{description} WHERE id = #{id}")
    void update(Survey survey);

    @Delete("DELETE FROM surveys WHERE id = #{id}")
    void delete(Long id);
}
