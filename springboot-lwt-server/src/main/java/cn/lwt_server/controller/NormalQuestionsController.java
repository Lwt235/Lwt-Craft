package cn.lwt_server.controller;

import cn.lwt_server.service.NormalQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/normalQuestions")
public class NormalQuestionsController {

    @Autowired
    NormalQuestionsService normalQuestionsService;

    @GetMapping("/getQuestions")
    public String getQuestions() {
        return normalQuestionsService.getQuestions();
    }
}
