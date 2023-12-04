package cn.lwt_server.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    String getFileList();
    void download(@PathVariable String fileName, HttpServletResponse response) throws IOException;
    String upload(String jwt, MultipartFile file) throws IOException;
    String deleteFile(String jwt, String url);
}
