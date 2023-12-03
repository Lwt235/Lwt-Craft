package cn.lwt_server.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.lwt_server.mapper.UserMapper;
import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.FileMessage;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/firstRequest")
    private String firstRequest() {
        Date date = new Date();
        Result result = new Result(0, "Success", date.toString());
        return JSON.toJSONString(result);
    }

    @GetMapping("/list")
    private String getUser() {
        List<User> userList = userMapper.list();
        Result result = new Result(0, "Success", JSON.toJSONString(userList));
        return JSON.toJSONString(result);
    }

    @GetMapping("/getById")
    private String getById(@RequestParam("id") long id) {
        User user = userMapper.getById(id);
        System.out.println(user);
        Result result = new Result(0, "Success", JSON.toJSONString(user));
        return JSON.toJSONString(result);
    }

    @GetMapping("/listBy")
    private String listBy(@RequestParam(value = "msg", required = false) String msg, @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime) {
        Timestamp sTime = null, eTime = null;
        if (msg != null && msg.equals(""))
            msg = null;
        if (startTime != null && !startTime.equals(""))
            sTime = Timestamp.valueOf(startTime);
        if (endTime != null && !endTime.equals(""))
            eTime = Timestamp.valueOf(endTime);
        List<User> userList = userMapper.listBy(msg, sTime, eTime);
        Result result = new Result(0, "Success", JSON.toJSONString(userList));
        return JSON.toJSONString(result);
    }

    @GetMapping("/getAuthority")
    private String getAuthority(@RequestHeader("token") String jwt) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        result = new Result(0, "Success", Authority);
        return JSON.toJSONString(result);
    }

    @GetMapping("/getFileList")
    public String getFileList() {
        List<FileMessage> fileList = userMapper.getFileList();
        Result result = new Result(0, "success", JSON.toJSONString(fileList));
        return JSON.toJSONString(result);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
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

    @PostMapping("/checkAccount")
    private String checkAccount(@RequestParam("name") String name, @RequestParam("password") String password) {
        Result result;
        System.out.println("检查：name: " + name + " password: " + password);
        Account account = userMapper.checkAccount(name);
        if (account != null) {
            System.out.println("返回: |" + account.getPassword() + "| ? |" + password + "|");
            if (account.getPassword().equals(password)) {

                Map<String, Object> claims = new HashMap<>();
                claims.put("id", account.getId());
                claims.put("name", account.getName());
                claims.put("authority", account.getAuthority());
                String secretKey = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";

                String jwt = Jwts.builder()
                        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256)
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                        .compact();

                result = new Result(0, "Success", jwt);
                System.out.println(result);
            } else {
                result = new Result(1, "WrongPassword", null);
            }
        } else {
            result = new Result(2, "AccountNotFound", null);
        }
        return JSON.toJSONString(result);
    }

    @PostMapping("/Add")
    private String Add(@RequestHeader("token") String jwt, @RequestParam("number") int number) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator") || Authority.equals("Guest")) {
            System.out.println("数字：" + number);
            result = new Result(0, "Success", String.valueOf(number + 1));
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @PostMapping("/insert")
    private String insert(@RequestHeader("token") String jwt, @RequestParam("msg") String msg, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            if (msg.equals("") || startTime.equals("") || endTime.equals("")) {
                result = new Result(1, "Error: missing 'msg' or 'startTime' or 'endTime'", null);
                return JSON.toJSONString(result);
            }
            User newUser = new User(System.currentTimeMillis(), msg, Timestamp.valueOf(startTime), Timestamp.valueOf(endTime), new Timestamp(System.currentTimeMillis()));
            if (newUser.getStartTime().after(newUser.getEndTime())) {
                result = new Result(2, "Error: The endTime is earlier than startTime", null);
            } else {
                userMapper.insert(newUser);
                result = new Result(0, "success in insert one item", newUser.toString());
            }
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @PostMapping("/upload")
    public String upload(@RequestHeader("token") String jwt, MultipartFile file) throws IOException {
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
            String url = "https://lwt-server.cn/api/download/" + originalFilename;
            //String url = "http://localhost:8081/api/download/" + originalFilename;
            System.out.println("url:" + url);
            userMapper.addFiles(originalFilename, url);
            result = new Result(0, "success", url);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @PutMapping("/update")
    private String update(@RequestHeader("token") String jwt, @RequestParam("id") long id, @RequestParam(value = "msg", required = false) String msg, @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            Timestamp sTime = null, eTime = null;
            if (msg.equals(""))
                msg = null;
            if (startTime != null && !startTime.equals(""))
                sTime = Timestamp.valueOf(startTime);
            if (endTime != null && !endTime.equals(""))
                eTime = Timestamp.valueOf(endTime);
            userMapper.update(new User(id, msg, sTime, eTime, new Timestamp(System.currentTimeMillis())));
            User user = userMapper.getById(id);
            result = new Result(0, "success in update item:", user.toString());
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @DeleteMapping("/delete")
    private String delete(@RequestHeader("token") String jwt, @RequestParam("id") long id) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            System.out.println("删除：" + id);
            userMapper.delete(id);
            result = new Result(0, "success in delete one item(id=" + id + ")", null);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @DeleteMapping("/deleteByIds")
    private String deleteByIds(@RequestHeader("token") String jwt, @RequestBody List<Long> ids) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            System.out.println("批量删除：" + ids.toString());
            userMapper.deleteByIds(ids);
            result = new Result(0, "success in delete items:\n" + ids, null);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @DeleteMapping("/deleteFile")
    private String deleteFile(@RequestHeader("token") String jwt, @RequestParam("url") String url) {
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
            userMapper.deleteFile(url);
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