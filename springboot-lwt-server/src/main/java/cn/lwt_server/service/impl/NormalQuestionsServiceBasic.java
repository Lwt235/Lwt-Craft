package cn.lwt_server.service.impl;

import cn.lwt_server.mapper.NormalQuestionsMapper;
import cn.lwt_server.pojo.Question;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.service.NormalQuestionsService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalQuestionsServiceBasic implements NormalQuestionsService {

    @Autowired
    NormalQuestionsMapper normalQuestionsMapper;

    @Override
    public String getQuestions() {
        Result result;
        List<Question> questions = normalQuestionsMapper.getQuestions();
        result = new Result(0,"Success", JSON.toJSONString(questions));
        return JSON.toJSONString(result);
    }
}
