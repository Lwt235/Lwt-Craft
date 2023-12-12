package cn.lwt_server.service;

public interface MailService {
    String sendMessageToEmail (String email);
    String checkVerifyCode (String verifyCode, String email, String name, String password, String authority);
    String resetSendToEmail (String email);
    String resetCheck (String verifyCode, String email);
    String resetPassword (String verifyCode, String email, String password);
}
