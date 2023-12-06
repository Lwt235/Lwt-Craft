package cn.lwt_server.controller;

import cn.lwt_server.pojo.Community;
import cn.lwt_server.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/list")
    private String list() {
        return communityService.list();
    }

    @GetMapping("/getById")
    private String getById(Integer id) {
        return communityService.getById(id);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        communityService.download(fileName, response);
    }

    @PostMapping("/upload")
    public String upload(@RequestHeader("token") String jwt, MultipartFile file) throws IOException {
        return communityService.upload(jwt, file);
    }

    @PostMapping("/insert")
    public String insert(@RequestHeader("token") String jwt, @RequestParam("nickname") String nickname, @RequestParam("avatarPath") String avatar, @RequestParam("title") String title, @RequestParam("detailedInformation") String detailedInformation, @RequestParam("linkPath") String linkPath, @RequestParam("time") String time) {
        return communityService.insert(jwt, nickname, avatar, title, detailedInformation, linkPath, time);
    }

    @PutMapping("/update")
    public String update(@RequestHeader("token") String jwt, @RequestParam("id") Integer id, @RequestParam(value = "nickname", required = false) String nickname, @RequestParam(value = "avatarPath", required = false) String avatarPath, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "detailedInformation", required = false) String detailedInformation, @RequestParam(value = "linkPath", required = false) String linkPath, @RequestParam(value = "time", required = false) String time) {
        Timestamp formatTime = null;
        if (nickname.equals(""))
            nickname = null;
        if (avatarPath.equals(""))
            avatarPath = null;
        if (title.equals(""))
            title = null;
        if (detailedInformation.equals(""))
            detailedInformation = null;
        if (linkPath.equals(""))
            linkPath = null;
        if (time != null && !time.equals(""))
            formatTime = Timestamp.valueOf(time);
        return communityService.update(jwt, id, nickname, avatarPath, title, detailedInformation, linkPath, formatTime);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestHeader("token") String jwt, @RequestParam("url") String url) {
        return communityService.deleteFile(jwt, url);
    }
}
