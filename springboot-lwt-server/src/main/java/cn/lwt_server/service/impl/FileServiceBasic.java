package cn.lwt_server.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.lwt_server.mapper.FileMapper;
import cn.lwt_server.pojo.FileMessage;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.service.FileService;
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
import java.util.List;

@Service
public class FileServiceBasic implements FileService {

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @Autowired
    private FileMapper fileMapper;

    @Override
    public String getFileList() {
        List<FileMessage> fileList = fileMapper.getFileList();
        Result result = new Result(0, "success", JSON.toJSONString(fileList));
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
        System.out.println("uploading... " + "Path: " + ROOT_PATH);
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            String originalFilename = file.getOriginalFilename();
            String mainName = FileNameUtil.mainName(originalFilename);
            String extName = FileNameUtil.extName(originalFilename);
            if (!FileUtil.exist(ROOT_PATH)) {
                FileUtil.mkdir(ROOT_PATH);
            }
            if (FileUtil.exist(ROOT_PATH + "//" + originalFilename)) {
                originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
            }
            File saveFile = new File(ROOT_PATH + "//" + originalFilename);
            file.transferTo(saveFile);
            String url = "https://lwt-server.cn/file/download/" + originalFilename;
            //String url = "http://localhost:8081/file/download/" + originalFilename;
            System.out.println("url:" + url);
            fileMapper.addFiles(originalFilename, url);
            result = new Result(0, "success", url);
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
            fileMapper.deleteFile(url);
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
