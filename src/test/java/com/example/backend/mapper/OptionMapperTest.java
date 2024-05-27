package com.example.backend.mapper;

import com.example.backend.entity.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@ExtendWith(SpringExtension.class)
@MybatisTest
@Sql(value = "/sql/cleanup.sql",executionPhase= Sql.ExecutionPhase.AFTER_TEST_CLASS )
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OptionMapperTest {

    @Autowired
    private OptionMapper optionMapper;

    @Test
    public void testInsertOption() {
        Option option = new Option(1L, "Multiple times a day", 1L);
        int result = optionMapper.insert(option);
        assertTrue(result > 0);
        assertNotNull(option.getId());
    }



    @Test
    public void testSelectOptionById() {
        Option option = optionMapper.selectByPrimaryKey(2L);
        assertNotNull(option);
    }

    @Test
    public void testSelectOptionByQuestionId() {
        Option option = optionMapper.selectByQuestionId(1L);
        assertNotNull(option);
    }

    @Test
    public void updateOption() {
        Option option = new Option(2L, "Multiple times a day", 1L);
        int result = optionMapper.updateByPrimaryKeySelective(option);
        assertTrue(result > 0);
    }

    @Test
    public void deleteOptionById() {
        int result = optionMapper.deleteByPrimaryKey(2L);
        assertTrue(result > 0);
    }

    @BeforeTestClass
    @Sql("/sql/data.sql")
    public void setup() {
    }



}
