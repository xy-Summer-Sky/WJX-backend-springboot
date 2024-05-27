package com.example.backend.mapper;

import com.example.backend.entity.Survey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@MybatisTest
@Sql(value = "/sql/cleanup.sql",executionPhase= Sql.ExecutionPhase.AFTER_TEST_CLASS )
public class SurveyMapperTest {

    @Autowired
    private SurveyMapper surveyMapper;

    @Test
    @Sql("/sql/feature-data.sql")  // 假设这个SQL文件中插入了一些初始数据
    public void testFindById() {
        Survey survey = surveyMapper.selectByPrimaryKey(1L);
        assertNotNull(survey);
        assertEquals("Feature Survey", survey.getTitle());
    }

    private void assertNotNull(Survey survey) {
    }

    @BeforeTestClass
    @Sql("/sql/data.sql")  // 指定你的 setup.sql 文件的路径
    public void setup() {
        // setup.sql 脚本将在每个测试方法之前执行
    }





}
