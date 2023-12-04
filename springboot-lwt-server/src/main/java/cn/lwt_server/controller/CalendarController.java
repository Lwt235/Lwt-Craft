package cn.lwt_server.controller;

import cn.lwt_server.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    public CalendarService calendarService;

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