package com.example.backend.service;


import com.example.backend.DTO.OptionDto;
import com.example.backend.entity.*;
import com.example.backend.mapper.OptionMapper;
import com.example.backend.mapper.QuestionMapper;
import com.example.backend.mapper.ResponseMapper;
import com.example.backend.service.serviceInterface.QuestionServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionServiceInter {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private OptionMapper optionMapper;

    @Autowired
    private ResponseMapper responseMapper;

    public Question addQuestion(Question question) {
        questionMapper.insert(question);
        System.out.println("question() = " + question.getText());
        System.out.println("question() = " + question.getId());

        questionMapper.updateOrder(question);
        return question;
    }

    public Question getQuestionById(Long id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    public List<Question> getAllQuestionsBySurveyId(Long surveyId) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andSurveyIdEqualTo(surveyId);
        example.setOrderByClause("`order` asc");
        return questionMapper.selectByExample(example);
    }

    public void updateQuestion(Question question) {
        questionMapper.updateByPrimaryKey(question);
    }

    public void deleteQuestion(Long id) {

        // 删除所有选项
        OptionExample optionExample = new OptionExample();
        optionExample.createCriteria().andQuestionIdEqualTo(id);
        List<Option> options = optionMapper.selectByExample(optionExample);
        for (Option option : options) {
            optionMapper.deleteByPrimaryKey(option.getId());
        }
        //将DTO映射到实体类

        for (Option option : options) {
            optionMapper.deleteByPrimaryKey(option.getId());
        }

        // 创建一个ResponseExample对象，用于构建查询条件
        ResponseExample responseExample = new ResponseExample();
        responseExample.createCriteria().andQuestionIdEqualTo(id);

        // 获取问题相关的所有响应
        List<Response> responses = responseMapper.selectByExample(responseExample);
        // 删除所有响应
        for (Response response : responses) {
            responseMapper.deleteByPrimaryKey(response.getId());
        }

        questionMapper.deleteByPrimaryKey(id);
    }

    public List<OptionDto> getOptionsForQuestion(Long questionId) {
        return questionMapper.getOptionsForQuestion(questionId);
    }

    public OptionDto addOption(OptionDto option) {


        return option;
    }

    public OptionDto updateOption(OptionDto option) {
        return option;
    }

    public void swapQuestions(Long questionId1, Long questionId2) {
        Question question1 = questionMapper.selectByPrimaryKey(questionId1);
        Question question2 = questionMapper.selectByPrimaryKey(questionId2);

        Long order1 = question1.getOrder();
        Long order2 = question2.getOrder();

        System.out.println("order1: " + order1);
        System.out.println("order2: " + order2);
        question1.setOrder(order2);
        question2.setOrder(order1);

        questionMapper.updateByPrimaryKey(question1);
        questionMapper.updateByPrimaryKey(question2);
    }



    //在当前问题之后复制一个问题，并返回新问题的ID，然后让之后所有问题的order加1
    public Question copyQuestion(Long questionId,Long surveyId){
        Question question = questionMapper.selectByPrimaryKey(questionId);
        Question newQuestion = new Question();
        newQuestion.setSurveyId(question.getSurveyId());
        newQuestion.setText(question.getText());
        newQuestion.setType(question.getType());
        newQuestion.setOrder(question.getOrder() + 1);

        //获取当前问题的所有选项

        //获取当前问题之后的所有问题
        QuestionExample example = new QuestionExample();
        example.createCriteria().andSurveyIdEqualTo(surveyId).andOrderGreaterThan(question.getOrder());
        List<Question> questions = questionMapper.selectByExample(example);

        // 将这些问题的 order 增加 1
        for (Question q : questions) {
            q.setOrder(q.getOrder() + 1);
            questionMapper.updateByPrimaryKey(q);
        }
        questionMapper.insert(newQuestion);
        // 获取当前问题的所有选项
        if(question.getType().equals("1")||question.getType().equals("2")) {
            OptionExample optionExample = new OptionExample();
            optionExample.createCriteria().andQuestionIdEqualTo(questionId);
            List<Option> options = optionMapper.selectByExample(optionExample);

            // 复制这些选项
            for (Option option : options) {
                Option newOption = new Option();
                newOption.setQuestionId(newQuestion.getId()); // 设置新问题的ID
                newOption.setOptionText(option.getOptionText());
                newOption.setOrder(option.getOrder());
                optionMapper.insert(newOption);
            }
        }
        return newQuestion;



    }
}
