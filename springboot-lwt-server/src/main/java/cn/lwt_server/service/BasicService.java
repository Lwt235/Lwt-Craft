package cn.lwt_server.service;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface BasicService {
    String firstRequest();
    String getAuthority(String jwt);
    String checkAccount(String name, String password);
    String Add(String jwt, int number);
}
