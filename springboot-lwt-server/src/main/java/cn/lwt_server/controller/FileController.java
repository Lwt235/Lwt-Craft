package cn.lwt_server.controller;

import cn.lwt_server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    public FileService fileService;

    @GetMapping("/getFileList")
    public String getFileList() {
        return fileService.getFileList();
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        fileService.download(fileName, response);
    }

    @PostMapping("/upload")
    public String upload(@RequestHeader("token") String jwt, MultipartFile file) throws IOException {
        return fileService.upload(jwt, file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestHeader("token") String jwt, @RequestParam("url") String url) {
        return fileService.deleteFile(jwt, url);
    }
}
