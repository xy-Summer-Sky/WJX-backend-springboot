package com.example.backend.mapper;

import com.example.backend.DTO.SurveyDto;
import com.example.backend.entity.Survey;
import com.example.backend.entity.SurveyExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Sort;

import java.util.List;

@Mapper
public interface SurveyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    long countByExample(SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int deleteByExample(SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int insert(Survey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int insertSelective(Survey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    List<Survey> selectByExampleWithBLOBs(SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    List<SurveyDto> selectByExample(SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    Survey selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int updateByExampleSelective(@Param("record") Survey record, @Param("example") SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int updateByExampleWithBLOBs(@Param("record") Survey record, @Param("example") SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int updateByExample(@Param("record") Survey record, @Param("example") SurveyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int updateByPrimaryKeySelective(Survey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int updateByPrimaryKeyWithBLOBs(Survey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table surveys
     *
     * @mbg.generated Mon May 27 17:12:41 CST 2024
     */
    int updateByPrimaryKey(Survey record);

    void update(Survey existingSurvey);


    List<Survey> selectAll();

    List<Survey> selectByUserId(Long userId);

    List<Survey> selectByUserIdSorted(Long userId, Sort createdAt);
}