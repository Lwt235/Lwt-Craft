package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NormalQuestionsMapper {

    //获取列表
    List<Question> getQuestions();
}
