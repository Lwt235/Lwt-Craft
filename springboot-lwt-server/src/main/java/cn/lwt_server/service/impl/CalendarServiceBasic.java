package cn.lwt_server.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.lwt_server.mapper.CalendarMapper;
import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.FileMessage;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.pojo.User;
import cn.lwt_server.service.CalendarService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class CalendarServiceBasic implements CalendarService {
    @Autowired
    private CalendarMapper calendarMapper;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";

    @Override
    public String firstRequest() {
        Date date = new Date();
        Result result = new Result(0, "Success", date.toString());
        return JSON.toJSONString(result);
    }

    @Override
    public String getUser() {
        List<User> userList = calendarMapper.list();
        Result result = new Result(0, "Success", JSON.toJSONString(userList));
        return JSON.toJSONString(result);
    }

    @Override
    public String getById(long id) {
        User user = calendarMapper.getById(id);
        System.out.println(user);
        Result result = new Result(0, "Success", JSON.toJSONString(user));
        return JSON.toJSONString(result);
    }

    @Override
    public String listBy(String msg, Timestamp startTime, Timestamp endTime) {
        List<User> userList = calendarMapper.listBy(msg, startTime, endTime);
        Result result = new Result(0, "Success", JSON.toJSONString(userList));
        return JSON.toJSONString(result);
    }

    @Override
    public String getAuthority(String jwt) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        result = new Result(0, "Success", Authority);
        return JSON.toJSONString(result);
    }





    @Override
    public String checkAccount(String name, String password) {
        Result result;
        System.out.println("检查：name: " + name + " password: " + password);
        Account account = calendarMapper.checkAccount(name);
        System.out.println("accout:"+account);
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

    @Override
    public String Add(String jwt, int number) {
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

    @Override
    public String insert(String jwt, String msg, String startTime, String endTime) {
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
                calendarMapper.insert(newUser);
                result = new Result(0, "success in insert one item", newUser.toString());
            }
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }



    @Override
    public String update(String jwt, long id, String msg, Timestamp startTime, Timestamp endTime) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            calendarMapper.update(new User(id, msg, startTime, endTime, new Timestamp(System.currentTimeMillis())));
            User user = calendarMapper.getById(id);
            result = new Result(0, "success in update item:", user.toString());
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String delete(String jwt, long id) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            System.out.println("删除：" + id);
            calendarMapper.delete(id);
            result = new Result(0, "success in delete one item(id=" + id + ")", null);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String deleteByIds(String jwt, List<Long> ids) {
        Result result;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                .parseClaimsJws(jwt)
                .getBody();
        String Authority = claims.get("authority", String.class);
        if (Authority.equals("Administrator")) {
            System.out.println("批量删除：" + ids.toString());
            calendarMapper.deleteByIds(ids);
            result = new Result(0, "success in delete items:\n" + ids, null);
        } else {
            result = new Result(1, "PermissionDenied", null);
        }
        return JSON.toJSONString(result);
    }


}
