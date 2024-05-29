package com.example.backend.service;

import com.example.backend.DTO.OptionDto;
import com.example.backend.DTO.QuestionDto;
import com.example.backend.DTO.SurveyDto;
import com.example.backend.entity.Question;
import com.example.backend.entity.Survey;
import com.example.backend.mapper.QuestionMapper;
import com.example.backend.mapper.SurveyMapper;
import com.example.backend.service.serviceInterface.SurveyServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyServiceInter {

    @Autowired
    private SurveyMapper surveyMapper;
    @Autowired
    private QuestionMapper questionMapper;

    private Survey convertToEntity(SurveyDto surveyDto) {
        Survey survey = new Survey();
        survey.setId(surveyDto.getId());
        survey.setTitle(surveyDto.getTitle());
        survey.setCreatedBy(surveyDto.getCreatedBy());
        survey.setDescription(surveyDto.getDescription());
        survey.setCreatedAt(surveyDto.getCreatedAt());
        return survey;
    }

    private SurveyDto convertSurveyToDto(Survey survey) {
        // 假设 Survey 有 getId(), getTitle(), getCreatedBy(), getDescription() 方法
        return new SurveyDto(survey.getId(), survey.getTitle(), survey.getCreatedBy(), survey.getDescription(), survey.getCreatedAt());
    }

    private QuestionDto convertQuestionToDto(Question question) {
        // 假设 Question 有相应的方法
        return new QuestionDto(question.getId(), question.getText(), question.getType(), question.getSurveyId());
    }

    private Question convertToEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setText(questionDto.getText());
        question.setType(questionDto.getType());
        question.setSurveyId(questionDto.getSurveyId());

        return question;
    }


    @Override
    public SurveyDto createSurvey(SurveyDto surveyDto) {
        Survey survey = convertToEntity(surveyDto);
        surveyMapper.insert(survey);
        return convertSurveyToDto(survey);
    }

    @Override
    public List<SurveyDto> getAllSurveys() {
        return surveyMapper.selectAll().stream().map(this::convertSurveyToDto).collect(Collectors.toList());
    }


    @Override
    public SurveyDto getSurveyById(Long id) {
        Survey survey = surveyMapper.selectByPrimaryKey(id);
        return convertSurveyToDto(survey);
    }

    @Override
    public SurveyDto updateSurvey(Long id, SurveyDto surveyDto) {
        Survey survey = convertToEntity(surveyDto);
        survey.setId(id); // Ensure ID is set correctly
        surveyMapper.updateByPrimaryKey(survey);
        return convertSurveyToDto(survey);
    }

    @Override
    public void deleteSurvey(Long id) {
        surveyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addQuestionToSurvey(Long surveyId, QuestionDto questionDto) {
        Question question = convertToEntity(questionDto);
        question.setSurveyId(surveyId); // Link question to the survey
        questionMapper.insert(question);
    }

    @Override
    public List<QuestionDto> getQuestionsForSurvey(Long surveyId) {
        List<Question> questions = questionMapper.selectBySurveyId(surveyId);
        return questions.stream().map(this::convertQuestionToDto).collect(Collectors.toList());
    }

    @Override
    public List<OptionDto> getOptionsForQuestion(Long questionId) {
        // Implementation would similarly fetch options and convert them to DTOs

        return null;
    }


    public List<SurveyDto> getSurveysByUserId(Long userId) {
        List<Survey> surveys = surveyMapper.selectByUserId(userId);
        return surveys.stream().map(this::convertSurveyToDto).collect(Collectors.toList());
    }


    public List<SurveyDto> getSurveysByUserIdSorted(Long userId) {
        Sort sort = Sort.by(Sort.Order.asc("createdAt"));
        List<Survey> surveys = surveyMapper.selectByUserIdSorted(userId, sort);
        return surveys.stream().map(this::convertSurveyToDto).collect(Collectors.toList());

    }
}
