package cn.lwt_server.controller;

import cn.lwt_server.service.BasicService;
import cn.lwt_server.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basic")
public class BasicController {

    @Autowired
    public BasicService basicService;

    @GetMapping("/firstRequest")
    public String firstRequest() {
        return basicService.firstRequest();
    }

    @GetMapping("/getAuthority")
    public String getAuthority(@RequestHeader("token") String jwt) {
        return basicService.getAuthority(jwt);
    }

    @PostMapping("/checkAccount")
    public String checkAccount(@RequestParam("name") String name, @RequestParam("password") String password) {
        return basicService.checkAccount(name, password);
    }

    @PostMapping("/Add")
    public String Add(@RequestHeader("token") String jwt, @RequestParam("number") int number) {
        return basicService.Add(jwt, number);
    }
}
