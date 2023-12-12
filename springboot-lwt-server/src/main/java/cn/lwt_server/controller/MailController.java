package cn.lwt_server.controller;

import cn.lwt_server.pojo.Account;
import cn.lwt_server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping("/code")
    public String sendMessageToEmail(@RequestParam("email") String email) {
        return mailService.sendMessageToEmail(email);
    }

    @PostMapping("/check")
    public String checkVerifyCode(@RequestParam("verifyCode") String verifyCode, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("authority") String authority) {
        return mailService.checkVerifyCode(verifyCode, email, name, password, authority);
    }

    @PostMapping("/resetSend")
    public String resetSendToEmail(@RequestParam("email") String email) {
        return mailService.resetSendToEmail(email);
    }

    @PostMapping("/resetCheck")
    public String resetCheck(@RequestParam("verifyCode") String verifyCode, @RequestParam("email") String email) {
        return mailService.resetCheck(verifyCode, email);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("verifyCode") String verifyCode, @RequestParam("email") String email, @RequestParam("password") String password) {
        return mailService.resetPassword(verifyCode, email, password);
    }

}
