package com.example.backend.service;

import com.example.backend.DTO.*;
import com.example.backend.entity.*;
import com.example.backend.mapper.*;
import com.example.backend.service.serviceInterface.SurveyServiceInter;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyServiceInter {

    private final SurveyMapper surveyMapper;
    private final QuestionMapper questionMapper;
    private final ResponseMapper responseMapper;
    private final SurveyStateMapper surveyStateMapper;
    private final OptionMapper optionMapper;// 新增
    private final UserMapper userMapper;

    // 新增
    public SurveyServiceImpl(SurveyMapper surveyMapper, QuestionMapper questionMapper, ResponseMapper responseMapper, SurveyStateMapper surveyStateMapper, UserMapper userMapper, OptionMapper optionMapper) {
        this.surveyMapper = surveyMapper;
        this.questionMapper = questionMapper;
        this.responseMapper = responseMapper;
        this.surveyStateMapper = surveyStateMapper;
        this.userMapper = userMapper;
        this.optionMapper = optionMapper;
    }


    private Survey convertToEntity(SurveyDto surveyDto) {
        Survey survey = new Survey();
        survey.setId(surveyDto.getId());
        survey.setTitle(surveyDto.getTitle());
        survey.setCreatedBy(surveyDto.getCreatedBy());
        survey.setDescription(surveyDto.getDescription());
//        survey.setCreatedAt(surveyDto.getCreatedAt());
        return survey;
    }

    private SurveyDto convertSurveyToDto(Survey survey) {
        SurveyState surveyState=surveyStateMapper.selectSurveyStateBySurveyId(survey.getId());
        System.out.println(surveyState);
        if(surveyState==null){
            surveyState=new SurveyState();
            surveyState.setSurveyId(survey.getId());
            surveyState.setReceivenumber(0);
            surveyState.setState("未发布");
            surveyStateMapper.insert(surveyState);
        }

        // 假设 Survey 有 getId(), getTitle(), getCreatedBy(), getDescription() 方法
        return new SurveyDto(survey.getId(), survey.getTitle(), survey.getCreatedBy(), survey.getDescription(), survey.getCreatedAt(),  surveyState.getState(),surveyState.getReceivenumber());
        //获取问卷的主状态一同赋值


    }

    private QuestionDto convertQuestionToDto(Question question) {
        // 假设 Question 有相应的方法
        return new QuestionDto(question.getId(), question.getText(), question.getType(), question.getSurveyId());
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
        surveyMapper.updateByPrimaryKeyWithBLOBs(survey);

        // After the update, fetch the survey again from the database
        Survey updatedSurvey = surveyMapper.selectByPrimaryKey(id);
        return convertSurveyToDto(updatedSurvey);
    }

    @Override
    public void deleteSurvey(Long id) {
        // 获取问卷相关的所有问题
        List<Question> questions = questionMapper.selectBySurveyId(id);
        for (Question question : questions) {
            // 创建一个OptionExample对象，用于构建查询条件
            OptionExample optionExample = new OptionExample();
            optionExample.createCriteria().andQuestionIdEqualTo(question.getId());
            // 获取问题相关的所有选项
            List<Option> options = optionMapper.selectByExample(optionExample);
            // 删除所有选项
            for (Option option : options) {
                optionMapper.deleteByPrimaryKey(option.getId());
            }

            // 创建一个ResponseExample对象，用于构建查询条件
            ResponseExample responseExample = new ResponseExample();
            responseExample.createCriteria().andQuestionIdEqualTo(question.getId());
            // 获取问题相关的所有回答
            List<Response> responses = responseMapper.selectByExample(responseExample);
            // 删除所有回答
            for (Response response : responses) {
                responseMapper.deleteByPrimaryKey(response.getId());
            }

            // 删除问题本身
            questionMapper.deleteByPrimaryKey(question.getId());
        }

        // 删除与问卷相关的surveys_state记录
        SurveyStateExample surveyStateExample = new SurveyStateExample();
        surveyStateExample.createCriteria().andSurveyIdEqualTo(id);
        surveyStateMapper.deleteByExample(surveyStateExample);

        surveyMapper.deleteByPrimaryKey(id);
    }



    @Override
    public List<QuestionDto> getQuestionsForSurvey(Long surveyId) {
        List<Question> questions = questionMapper.selectBySurveyId(surveyId);
        return questions.stream().map(this::convertQuestionToDto).collect(Collectors.toList());
    }

    //未实现
    @Override
    public List<OptionDto> getOptionsForQuestion(Long questionId) {


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

    public List<QuestionResponse> getResponsesForSurvey(Long surveyId) {
        List<QuestionDto> questions = getQuestionsForSurvey(surveyId);
        List<QuestionResponse> allResponses = new ArrayList<>();

        ResponseServiceImpl responseService;
        responseService = new ResponseServiceImpl(responseMapper);
        for (QuestionDto question : questions) {
            List<ResponseDto> responses = responseService.getResponsesForQuestion(question.getId());

            System.out.println(responses);
            for(ResponseDto response : responses){
                if(response.getanswerText() == null){
                    System.out.println("Response is null");
                }

                System.out.println("Question: " + question.getText() + " Response: " + response.getanswerText());
                allResponses.add(new QuestionResponse(question.getText(), response.answerText()));
            }

        }




        return allResponses;
    }

    public SurveyStateDto getSurveyState(Long surveyId) {

       SurveyState surveyState=surveyStateMapper.selectSurveyStateBySurveyId(surveyId);


        return new SurveyStateDto(surveyState.getSurveyId(), surveyState.getReceivenumber(), surveyState.getState());

    }

    public SurveyStateDto incrementSurveyState(Long surveyId) {
        SurveyState surveyState = surveyStateMapper.selectSurveyStateBySurveyId(surveyId);
        surveyState.setReceivenumber(surveyState.getReceivenumber() + 1);
        surveyStateMapper.updateByPrimaryKey(surveyState);
        return new SurveyStateDto(surveyState.getSurveyId(), surveyState.getReceivenumber(), surveyState.getState());
    }

    public SurveyStateDto changeSurveyState(SurveyStateDto surveyStateDto) {
        SurveyState surveyState = surveyStateMapper.selectSurveyStateBySurveyId(surveyStateDto.getSurveyId());
        surveyState.setState(surveyStateDto.getState());
        surveyStateMapper.updateByPrimaryKey(surveyState);
        return new SurveyStateDto(surveyState.getSurveyId(), surveyState.getReceivenumber(), surveyState.getState());
    }

    public SurveyStateDto addSurveyState(Long surveyId) {
        SurveyState surveyState = new SurveyState();
        surveyState.setSurveyId(surveyId);
        surveyState.setReceivenumber(0);
        surveyState.setState("未发布");
        surveyStateMapper.insert(surveyState);
        return new SurveyStateDto(surveyState.getSurveyId(), surveyState.getReceivenumber(), surveyState.getState());
    }


    public UserDetails loadUserByUsername(String username) {
        User user = userMapper.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        // 转换为 UserDetails 对象并返回
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("Null") // 设置用户角色，可以根据具体需求修改
                .build();

    }

    public boolean isSurveyCreator(String username, String surveyId) {
        User user = userMapper.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        Survey survey = surveyMapper.selectByPrimaryKey(Long.parseLong(surveyId));
        if (survey == null) {
            return false;
        }
        return survey.getCreatedBy().equals(user.getId());
    }

}

