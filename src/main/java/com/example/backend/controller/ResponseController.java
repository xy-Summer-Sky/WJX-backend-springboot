package com.example.backend.controller;

import com.example.backend.DTO.ResponseDto;
import com.example.backend.config.SecurityConfig;
import com.example.backend.service.ResponseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//ResponseController类是一个控制器类，用于处理回复相关的HTTP请求。
@RestController
@RequestMapping("/api/responses")
public class ResponseController extends SecurityConfig {

    private final ResponseServiceImpl responseServiceImpl;

    public ResponseController(ResponseServiceImpl responseServiceImpl) {
        this.responseServiceImpl = responseServiceImpl;
    }

    //给某个问题添加回复
    @CrossOrigin
    @PostMapping("/{questionId}")
    public ResponseEntity<ResponseDto> addResponseToQuestion(@PathVariable Long questionId, @RequestBody ResponseDto responseDto) {
        ResponseDto createdResponse = responseServiceImpl.addResponseToQuestion(questionId, responseDto);
        return ResponseEntity.ok(createdResponse);
    }

    //根据问题id获取回复
    @CrossOrigin
    @GetMapping("/{questionId}")
    public ResponseEntity<List<ResponseDto>> getResponsesForQuestion(@PathVariable Long questionId) {
        List<ResponseDto> responses = responseServiceImpl.getResponsesForQuestion(questionId);
        return ResponseEntity.ok(responses);
    }

    //根据回复id删除回复
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponse(@PathVariable Long id) {
        responseServiceImpl.deleteResponse(id);
        return ResponseEntity.ok().build();
    }
}
