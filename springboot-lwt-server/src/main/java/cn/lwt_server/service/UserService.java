package cn.lwt_server.service;

import cn.hutool.core.io.FileUtil;
import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.FileMessage;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.pojo.User;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    String firstRequest();
    String getUser();
    String getById(long id);
    String listBy(String msg, Timestamp startTime, Timestamp endTime);
    String getAuthority(String jwt);
    String getFileList();
    void download(@PathVariable String fileName, HttpServletResponse response) throws IOException;
    String checkAccount(String name, String password);
    String Add(String jwt, int number);
    String insert(String jwt, String msg, String startTime, String endTime);
    String upload(String jwt, MultipartFile file) throws IOException;
    String update(String jwt, long id, String msg, Timestamp startTime, Timestamp endTime);
    String delete(String jwt, long id);
    String deleteByIds(String jwt, List<Long> ids);
    String deleteFile(String jwt, String url);
}
