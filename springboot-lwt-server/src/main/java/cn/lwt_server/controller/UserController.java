package cn.lwt_server.controller;

import cn.lwt_server.service.UserService;
import cn.lwt_server.service.impl.UserServiceBasic;
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
public class UserController {

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @Autowired
    public UserService userService;

    @GetMapping("/firstRequest")
    public String firstRequest() {
        return userService.firstRequest();
    }

    @GetMapping("/list")
    public String getUser() {
        return userService.getUser();
    }

    @GetMapping("/getById")
    public String getById(@RequestParam("id") long id) {
        return userService.getById(id);
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
        return userService.listBy(msg, sTime, eTime);
    }

    @GetMapping("/getAuthority")
    public String getAuthority(@RequestHeader("token") String jwt) {
        return userService.getAuthority(jwt);
    }

    @GetMapping("/getFileList")
    public String getFileList() {
        return userService.getFileList();
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        userService.download(fileName, response);
    }

    @PostMapping("/checkAccount")
    public String checkAccount(@RequestParam("name") String name, @RequestParam("password") String password) {
        return userService.checkAccount(name, password);
    }

    @PostMapping("/Add")
    public String Add(@RequestHeader("token") String jwt, @RequestParam("number") int number) {
        return userService.Add(jwt, number);
    }

    @PostMapping("/insert")
    public String insert(@RequestHeader("token") String jwt, @RequestParam("msg") String msg, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return userService.insert(jwt, msg, startTime, endTime);
    }

    @PostMapping("/upload")
    public String upload(@RequestHeader("token") String jwt, MultipartFile file) throws IOException {
        return userService.upload(jwt, file);
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
        return userService.update(jwt, id, msg, sTime, eTime);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestHeader("token") String jwt, @RequestParam("id") long id) {
        return userService.delete(jwt, id);
    }

    @DeleteMapping("/deleteByIds")
    public String deleteByIds(@RequestHeader("token") String jwt, @RequestBody List<Long> ids) {
        return userService.deleteByIds(jwt, ids);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestHeader("token") String jwt, @RequestParam("url") String url) {
        return userService.deleteFile(jwt, url);
    }
}