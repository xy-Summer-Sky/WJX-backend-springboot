package com.example.backend.controller;

import com.example.backend.DTO.SurveyDto;
import com.example.backend.config.SecurityConfig;
import com.example.backend.service.SurveyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//
@RestController
@RequestMapping("/api/surveys")
public class SurveyController extends SecurityConfig {

    private final SurveyServiceImpl surveyService;

    public SurveyController(SurveyServiceImpl surveyService) {
        this.surveyService = surveyService;
    }

    //问卷的增加
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody SurveyDto surveyDto) {
        SurveyDto createdSurvey = surveyService.createSurvey(surveyDto);
        return ResponseEntity.ok(createdSurvey);
    }

    //所有问卷的获取
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        List<SurveyDto> surveys = surveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }


    //根据id获取问卷
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable Long id) {
        SurveyDto survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok(survey);
    }

    //根据用户id获取所有问卷
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SurveyDto>> getSurveysByUserId(@PathVariable Long userId) {
        List<SurveyDto> surveys = surveyService.getSurveysByUserId(userId);
        return ResponseEntity.ok(surveys);
    }

    //根据问卷id的更新
    @PutMapping("/{id}")
    public ResponseEntity<SurveyDto> updateSurvey(@PathVariable Long id, @RequestBody SurveyDto surveyDto) {
        SurveyDto updatedSurvey = surveyService.updateSurvey(id, surveyDto);
        return ResponseEntity.ok(updatedSurvey);
    }

    //问卷的删除
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.ok().build();
    }

    //根据用胡id获取所有问卷并且按照时间排序(按照时间从小到大)
    @GetMapping("/user/{userId}/sorted")
    public ResponseEntity<List<SurveyDto>> getSurveysByUserIdSorted(@PathVariable Long userId) {
        List<SurveyDto> surveys = surveyService.getSurveysByUserIdSorted(userId);
        return ResponseEntity.ok(surveys);
    }
}
