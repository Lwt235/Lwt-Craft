package cn.lwt_server.service.impl;

import cn.lwt_server.mapper.MailMapper;
import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.Mail;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.service.MailService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class MailServiceBasic implements MailService {

    @Autowired
    MailMapper mailMapper;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    private void insertInformation(String verifyCode, String email) {
        if (mailMapper.getByAddress(email) != null) {
            mailMapper.delete(email);
        }
        mailMapper.insert(email, verifyCode, new Timestamp(System.currentTimeMillis()));
    }

    //校验邮箱
    private static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }

    private boolean sendEmail(String email, String verifyCode) {
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(verifyCode.split("")));
        context.setVariable("Code", verifyCode);
        String process = templateEngine.process("EmailVerificationCode.html", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("【Lwt's World】验证码");
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSentDate(new Date());
            helper.setText(process, true);
        } catch (MessagingException e) {
            return false;
        }
        javaMailSender.send(mimeMessage);
        insertInformation(verifyCode, email);
        return true;
    }

    private String createVerifyCode() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer Code = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(36);
            Code.append(str.charAt(number));
        }
        return Code.toString();
    }

    private int checkCode(String verifyCode, String email) {
        List<Mail> items = mailMapper.getByAddress(email);
        Mail item = items.get(items.size() - 1);
        if (item == null) {
            return 1;
        } else if (!item.getVerifyCode().equals(verifyCode)) {
            return 2;
        } else if (System.currentTimeMillis() - item.getTime().getTime() > 300000l) {
            mailMapper.delete(email);
            return 3;
        } else {
            mailMapper.delete(email);
            return 0;
        }
    }

    @Override
    public String sendMessageToEmail(String email) {
        Result result;
        if (StringUtils.isEmpty(email)) {
            result = new Result(1, "Empty Email!", null);
            return JSON.toJSONString(result);
        }

        if (!isValidEmail(email)) {
            result = new Result(2, "Invalid Email!", null);
            return JSON.toJSONString(result);
        } else if (mailMapper.isRepeat(email).size() != 0) {
            result = new Result(3, "Email is exist!", null);
            return JSON.toJSONString(result);
        }


        if (sendEmail(email, createVerifyCode())) {
            result = new Result(0, "Success send email", null);
        } else {
            result = new Result(1, "Failed to send email", null);
        }
        return JSON.toJSONString(result);


    }

    @Override
    public String checkVerifyCode(String verifyCode, String email, String name, String password, String authority) {
        Result result;
        int Code = checkCode(verifyCode, email);
        if (Code == 1) {
            result = new Result(1, "Can't found email", null);
            return JSON.toJSONString(result);
        } else if (Code == 2) {
            result = new Result(2, "Not match", null);
            return JSON.toJSONString(result);
        } else if (Code == 3) {
            result = new Result(3, "The code is invalid", null);
            return JSON.toJSONString(result);
        } else {
            result = new Result(0, "Success", null);
            mailMapper.addAccount(name, password, authority, email);
            return JSON.toJSONString(result);
        }
    }

    @Override
    public String resetSendToEmail(String email) {
        Result result;
        if (StringUtils.isEmpty(email)) {
            result = new Result(1, "Empty Email!", null);
            return JSON.toJSONString(result);
        }

        List<Account> accounts = mailMapper.isRepeat(email);
        if (!isValidEmail(email)) {
            result = new Result(2, "Invalid Email!", null);
            return JSON.toJSONString(result);
        } else if (accounts.size() == 0) {
            result = new Result(3, "No such account", null);
            return JSON.toJSONString(result);
        }


        if (sendEmail(email, createVerifyCode())) {
            result = new Result(0, "Success send email", JSON.toJSONString(accounts.get(accounts.size() - 1)));
        } else {
            result = new Result(1, "Failed to send email", null);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String resetCheck(String verifyCode, String email) {
        Result result;
        int Code = checkCode(verifyCode, email);
        if (Code == 1) {
            result = new Result(1, "Can't found email", null);
            return JSON.toJSONString(result);
        } else if (Code == 2) {
            result = new Result(2, "Not match", null);
            return JSON.toJSONString(result);
        } else if (Code == 3) {
            result = new Result(3, "The code is invalid", null);
            return JSON.toJSONString(result);
        } else {
            result = new Result(0, "Success", null);
            mailMapper.insert(email, verifyCode, new Timestamp(System.currentTimeMillis()));
            return JSON.toJSONString(result);
        }
    }

    @Override
    public String resetPassword(String verifyCode, String email, String password) {
        Result result;
        int Code = checkCode(verifyCode, email);
        if (Code == 0) {
            result = new Result(0, "Success", null);
            mailMapper.editAccount(email, password);
            return JSON.toJSONString(result);
        }
        result = new Result(1, "Unknown error", null);
        return JSON.toJSONString(result);
    }

}