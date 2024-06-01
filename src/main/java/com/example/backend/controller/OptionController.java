package com.example.backend.controller;

import com.example.backend.DTO.OptionDto;
import com.example.backend.config.SecurityConfig;
import com.example.backend.service.OptionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//OptionController类是一个控制器类，用于处理选项相关的HTTP请求。
@RestController
@RequestMapping("/api/options")
@CrossOrigin(origins = "http://localhost:8082")
public class OptionController extends SecurityConfig{

    private final OptionServiceImpl optionServiceImpl;

    public OptionController(OptionServiceImpl optionServiceImpl) {
        this.optionServiceImpl = optionServiceImpl;
    }

    //给某个问题添加选项
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/{questionId}")
    public ResponseEntity<OptionDto> addOptionToQuestion(@PathVariable Long questionId, @RequestBody OptionDto optionDto) {
        OptionDto createdOption = optionServiceImpl.addOptionToQuestion(questionId, optionDto);
        return ResponseEntity.ok(createdOption);
    }

    //根据问题的id获取选项
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/{questionId}")
    public ResponseEntity<List<OptionDto>> getOptionsForQuestion(@PathVariable Long questionId) {
        List<OptionDto> options = optionServiceImpl.getOptionsForQuestion(questionId);
        return ResponseEntity.ok(options);
    }

    //根据选项id更新选项
    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/{id}")
    public ResponseEntity<OptionDto> updateOption(@PathVariable Long id, @RequestBody OptionDto optionDto) {
        OptionDto updatedOption = optionServiceImpl.updateOption(id, optionDto);
        return ResponseEntity.ok(updatedOption);
    }

    //根据选项id删除选项
    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable Long id) {
        optionServiceImpl.deleteOption(id);
        return ResponseEntity.ok().build();
    }

    //根据问题id获取选项
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<OptionDto>> getOptionsByQuestionId(@PathVariable Long questionId) {
        List<OptionDto> options = optionServiceImpl.getOptionsByQuestionId(questionId);
        return ResponseEntity.ok(options);
    }
}
