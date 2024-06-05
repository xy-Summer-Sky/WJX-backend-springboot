package com.example.backend.controller;

import com.example.backend.DTO.QuestionResponse;
import com.example.backend.DTO.SurveyDto;
import com.example.backend.DTO.SurveyStateDto;
import com.example.backend.service.ExcelFileCreator;
import com.example.backend.service.SurveyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

//
@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyServiceImpl surveyService;
    private final ExcelFileCreator excelFileCreator;
    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    public SurveyController(SurveyServiceImpl surveyService, ExcelFileCreator excelFileCreator) {
        this.surveyService = surveyService;


        this.excelFileCreator = excelFileCreator;
    }

    //问卷的增加
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/create")
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody SurveyDto surveyDto) {
        SurveyDto createdSurvey = surveyService.createSurvey(surveyDto);
        return ResponseEntity.ok(createdSurvey);
    }


    //根据id获取问卷
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable Long id) {
        SurveyDto survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok(survey);
    }

    //根据用户id获取所有问卷
    @CrossOrigin
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
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping("/user/{userId}/sorted")
    public ResponseEntity<List<SurveyDto>> getSurveysByUserIdSorted(@PathVariable Long userId) {
        List<SurveyDto> surveys = surveyService.getSurveysByUserIdSorted(userId);
        return ResponseEntity.ok(surveys);
    }




    @CrossOrigin
    @GetMapping("/responses/download/{surveyId}")
    public ResponseEntity<InputStreamResource> downloadSurveyResponses(@PathVariable Long surveyId) {
        List<QuestionResponse> responses = surveyService.getResponsesForSurvey(surveyId);
        // Use the injected excelFileCreator instance
        String filePath = excelFileCreator.createExcel(responses);

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(fileInputStream));
        } catch (IOException e) {
            logger.error("Error occurred while downloading survey responses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //返回问卷的状态信息
    @CrossOrigin
    @GetMapping("/state/{surveyId}")
    public ResponseEntity<SurveyStateDto> getSurveyState(@PathVariable Long surveyId) {



        return ResponseEntity.ok(surveyService.getSurveyState(surveyId));

    }

    //问卷的接收数量加一
    @CrossOrigin
    @PutMapping("/state/addNum/{surveyId}")
    public ResponseEntity<SurveyStateDto> incrementSurveyState(@PathVariable Long surveyId) {
        SurveyStateDto surveyState = surveyService.incrementSurveyState(surveyId);
        return ResponseEntity.ok(surveyState);
    }

    //修改问卷状态
    @CrossOrigin
    @PutMapping("/state/changeSurveyState/{surveyId}")
    public ResponseEntity<SurveyStateDto> changeSurveyState(@RequestBody SurveyStateDto surveyStateDto) {
        SurveyStateDto surveyState = surveyService.changeSurveyState(surveyStateDto);
        return ResponseEntity.ok(surveyState);
    }

    //根据问卷id添加问卷状态
    @CrossOrigin
    @PostMapping("/addState/{surveyId}")
    public ResponseEntity<SurveyStateDto> addSurveyState(@PathVariable Long surveyId) {
        SurveyStateDto surveyState = surveyService.addSurveyState(surveyId);
        return ResponseEntity.ok(surveyState);
    }




}