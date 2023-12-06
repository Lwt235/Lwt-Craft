package cn.lwt_server.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public interface CommunityService {
    String list();
    String getById(int id);
    void download(String fileName, HttpServletResponse response) throws IOException;
    String upload(String jwt, MultipartFile file) throws IOException;
    String insert(String jwt, String nickname, String avatarPath, String title, String detailedInformation, String linkPath, String time);
    String update(String jwt, Integer id, String nickname, String avatarPath, String title, String detailedInformation, String linkPath, Timestamp time);
    String deleteFile(String jwt, String url);
}
