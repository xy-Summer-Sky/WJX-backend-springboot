package com.example.backend.service;

import com.example.backend.DTO.QuestionResponse;
import com.example.backend.config.SecurityConfig;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelFileCreator extends SecurityConfig {

    public String createExcel(List<QuestionResponse> responses) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Survey Responses");

        // Use LinkedHashMap to store questions and answers to maintain order and avoid duplicate columns
        Map<String, List<String>> questionAnswerMap = new LinkedHashMap<>();
        for (QuestionResponse response : responses) {
            questionAnswerMap.putIfAbsent(response.getQuestion(), new ArrayList<>());
            questionAnswerMap.get(response.getQuestion()).add(response.getAnswer());
        }

        // Create a row for the question headers
        Row headerRow = sheet.createRow(0);
        int headerCellNum = 0;
        for (String question : questionAnswerMap.keySet()) {
            headerRow.createCell(headerCellNum++).setCellValue(question);
        }

        // Create a row for each response
        int maxResponses = questionAnswerMap.values().stream().mapToInt(List::size).max().orElse(0);
        for (int i = 0; i < maxResponses; i++) {
            Row responseRow = sheet.createRow(i + 1);
            int responseCellNum = 0;
            for (List<String> answers : questionAnswerMap.values()) {
                String answer = i < answers.size() ? answers.get(i) : ""; // Get answer if exists, else empty string
                responseRow.createCell(responseCellNum++).setCellValue(answer);
            }
        }

        String filePath = "survey_responses.xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }
}