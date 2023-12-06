package cn.lwt_server.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.lwt_server.mapper.CommunityMapper;
import cn.lwt_server.pojo.Calendar;
import cn.lwt_server.pojo.Community;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.service.CommunityService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CommunityServiceBasic implements CommunityService {

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "community";

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public String list() {
        List<Community> communityList = communityMapper.list();
        System.out.println(communityList);
        Result result = new Result(0, "Success", JSON.toJSONString(communityList));
        return JSON.toJSONString(result);
    }

    @Override
    public String getById(int id) {
        Community community = communityMapper.getById(id);
        System.out.println(community);
        Result result = new Result(0, "Success", JSON.toJSONString(community));
        return JSON.toJSONString(result);
    }

    @Override
    public void download(String fileName, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        String filePath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public String upload(String jwt, MultipartFile file) throws IOException {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            System.out.println("uploading... " + "Path: " + ROOT_PATH);
            String originalFilename = file.getOriginalFilename();
            if (!FileUtil.exist(ROOT_PATH)) {
                FileUtil.mkdir(ROOT_PATH);
            }
            if (!FileUtil.exist(ROOT_PATH + "//" + originalFilename)) {
                File saveFile = new File(ROOT_PATH + "//" + originalFilename);
                file.transferTo(saveFile);
            }
            String url = "https://lwt-server.cn/community/download/" + originalFilename;
            //String url = "http://localhost:8081/community/download/" + originalFilename;
            result = new Result(0, "success", url);
            return JSON.toJSONString(result);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String insert(String jwt, String nickname, String avatarPath, String title, String detailedInformation, String linkPath, String time) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            if (nickname.equals("") || avatarPath.equals("") || title.equals("") || detailedInformation.equals("") || time == null) {
                result = new Result(1, "Error: missing parameter", null);
                return JSON.toJSONString(result);
            }
            Community newCommunity = new Community(null, nickname, avatarPath, title, detailedInformation, linkPath, Timestamp.valueOf(time));
            communityMapper.insert(newCommunity);
            result = new Result(0, "success in insert one item", newCommunity.toString());
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String update(String jwt, Integer id, String nickname, String avatarPath, String title, String detailedInformation, String linkPath, Timestamp time) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            communityMapper.update(new Community(id, nickname, avatarPath, title, detailedInformation, linkPath, time));
            Community community = communityMapper.getById(id);
            result = new Result(0, "success in update item:", community.toString());
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String deleteFile(String jwt, String url) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            System.out.println("删除：" + url);
            String realPath = ROOT_PATH + "/" + url.substring(35);
            System.out.println("readPath" + realPath);
            File file = new File(realPath);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            result = new Result(0, "success in delete one item(url=" + url + ")", null);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }
}
