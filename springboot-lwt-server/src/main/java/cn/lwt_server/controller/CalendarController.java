package cn.lwt_server.controller;

import cn.lwt_server.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class CalendarController {

    @Autowired
    public CalendarService calendarService;

    @GetMapping("/firstRequest")
    public String firstRequest() {
        return calendarService.firstRequest();
    }

    @GetMapping("/list")
    public String getUser() {
        return calendarService.getUser();
    }

    @GetMapping("/getById")
    public String getById(@RequestParam("id") long id) {
        return calendarService.getById(id);
    }

    @GetMapping("/listBy")
    public String listBy(@RequestParam(value = "msg", required = false) String msg, @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime) {
        Timestamp sTime = null, eTime = null;
        if (msg != null && msg.equals(""))
            msg = null;
        if (startTime != null && !startTime.equals(""))
            sTime = Timestamp.valueOf(startTime);
        if (endTime != null && !endTime.equals(""))
            eTime = Timestamp.valueOf(endTime);
        return calendarService.listBy(msg, sTime, eTime);
    }

    @GetMapping("/getAuthority")
    public String getAuthority(@RequestHeader("token") String jwt) {
        return calendarService.getAuthority(jwt);
    }

    @PostMapping("/checkAccount")
    public String checkAccount(@RequestParam("name") String name, @RequestParam("password") String password) {
        return calendarService.checkAccount(name, password);
    }

    @PostMapping("/Add")
    public String Add(@RequestHeader("token") String jwt, @RequestParam("number") int number) {
        return calendarService.Add(jwt, number);
    }

    @PostMapping("/insert")
    public String insert(@RequestHeader("token") String jwt, @RequestParam("msg") String msg, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return calendarService.insert(jwt, msg, startTime, endTime);
    }

    @PutMapping("/update")
    public String update(@RequestHeader("token") String jwt, @RequestParam("id") long id, @RequestParam(value = "msg", required = false) String msg, @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime) {
        Timestamp sTime = null, eTime = null;
        if (msg.equals(""))
            msg = null;
        if (startTime != null && !startTime.equals(""))
            sTime = Timestamp.valueOf(startTime);
        if (endTime != null && !endTime.equals(""))
            eTime = Timestamp.valueOf(endTime);
        return calendarService.update(jwt, id, msg, sTime, eTime);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestHeader("token") String jwt, @RequestParam("id") long id) {
        return calendarService.delete(jwt, id);
    }

    @DeleteMapping("/deleteByIds")
    public String deleteByIds(@RequestHeader("token") String jwt, @RequestBody List<Long> ids) {
        return calendarService.deleteByIds(jwt, ids);
    }
}