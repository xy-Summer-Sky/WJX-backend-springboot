package com.example.backend.mapper;

import com.example.backend.entity.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
@ExtendWith(SpringExtension.class)
@MybatisTest
@Sql(value = "/sql/cleanup.sql",executionPhase= Sql.ExecutionPhase.AFTER_TEST_CLASS )
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 使用真实数据库
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void testInsertQuestion() {
        Question question = new Question(null, "How often do you use our product?", "SINGLE_CHOICE", 1L);
        int result = questionMapper.insert(question);
        assertTrue(result > 0);  // 确保插入成功
        assertNotNull(question.getId());  // 确保ID被数据库正确赋值
    }

    @Test
    public void testSelectQuestionById() {
        // 需要确保数据库中有ID为1的记录
        Question question = questionMapper.selectByPrimaryKey(1L);
        assertNotNull(question);
    }

    @BeforeTestClass
    @Sql("/sql/data.sql")  // 指定你的 setup.sql 文件的路径
    public void setup() {
        // setup.sql 脚本将在每个测试方法之前执行
    }




}
