package com.example.backend.controller;


import com.example.backend.DTO.OptionDto;
import com.example.backend.config.SecurityConfig;
import com.example.backend.entity.Question;
import com.example.backend.service.QuestionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//QuestionController类是一个控制器类，用于处理问题相关的HTTP请求。
@RestController
@RequestMapping("/api/surveys/{surveyId}/questions")
public class QuestionController extends SecurityConfig {
    private final QuestionServiceImpl questionServiceImpl;

    public QuestionController(QuestionServiceImpl questionServiceImpl) {
        this.questionServiceImpl = questionServiceImpl;
    }

    //给某个问卷添加问题
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping
    public ResponseEntity<Question> addQuestion(@PathVariable Long surveyId, @RequestBody Question question) {
        question.setSurveyId(surveyId);
        Question createdQuestion = questionServiceImpl.addQuestion(question);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    //根据问题id获取问题
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionServiceImpl.getQuestionById(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    //根据问卷id获取所有问题
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestionsBySurveyId(@PathVariable Long surveyId) {
        List<Question> questions = questionServiceImpl.getAllQuestionsBySurveyId(surveyId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    //根据问题id更新问题
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody Question question) {
        question.setId(questionId);
        questionServiceImpl.updateQuestion(question);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    //根据问题id删除问题
    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionServiceImpl.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    //根据问题id和选项id更新选项
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{questionId}/options/{optionId}")
    public ResponseEntity<OptionDto> updateOption(@PathVariable Long questionId, @PathVariable Long optionId, @RequestBody OptionDto option) {
        option.setQuestionId(questionId);
        option.setId(optionId);
        OptionDto updatedOption = questionServiceImpl.updateOption(option);
        return new ResponseEntity<>(updatedOption, HttpStatus.OK);
    }

    //交换两个问题的顺序
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{questionId1}/swap/{questionId2}")
    public ResponseEntity<Void> swapQuestions(@PathVariable Long questionId1, @PathVariable Long questionId2) {
        questionServiceImpl.swapQuestions(questionId1, questionId2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Copy question
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/{questionId}/copy")
    public ResponseEntity<Question> copyQuestion(@PathVariable Long questionId, @PathVariable Long surveyId) {
        Question copiedQuestion = questionServiceImpl.copyQuestion(questionId,surveyId);
        return new ResponseEntity<>(copiedQuestion, HttpStatus.CREATED);
    }



}
