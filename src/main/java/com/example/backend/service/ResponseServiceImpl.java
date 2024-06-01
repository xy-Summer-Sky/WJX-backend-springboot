package com.example.backend.service;

import com.example.backend.DTO.ResponseDto;
import com.example.backend.entity.Response;
import com.example.backend.entity.ResponseExample;
import com.example.backend.mapper.ResponseMapper;
import com.example.backend.service.serviceInterface.ResponseServiceInter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseServiceImpl implements ResponseServiceInter {



    private final ResponseMapper responseMapper ;

    public ResponseServiceImpl(ResponseMapper responseMapper) {
        this.responseMapper = responseMapper;
    }

    public Response saveResponse(Response response) {
        responseMapper.insert(response);
        return response;
    }

    public List<ResponseDto> getResponsesForQuestion(Long questionId) {
        ResponseExample example = new ResponseExample();
        example.createCriteria().andQuestionIdEqualTo(questionId);
        List<Response> responses = responseMapper.selectByExampleWithBLOBs(example);
        System.out.println(responses);
        //未知原因，让回答的内容一直为空
        for(Response response:responses){
            System.out.println(response.getAnswerText());
            if(response.getAnswerText()==null){
                response.setAnswerText("未回答");
            }
        }
        System.out.println(responseMapper.selectByExampleWithBLOBs(example));
        return responses.stream().map(response -> new ResponseDto( response.getQuestionId(),response.getAnswerText())).collect(Collectors.toList());
    }

    public ResponseDto addResponseToQuestion(Long questionId, ResponseDto responseDto) {

        Response response = new Response();
        response.setQuestionId(questionId);
        response.setAnswerText(responseDto.answerText());
        responseMapper.insert(response);
        return responseDto;}

    public void deleteResponse(Long id) {
        responseMapper.deleteByPrimaryKey(id);

    }
}
