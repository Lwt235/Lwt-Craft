package cn.lwt_server.controller;

import cn.lwt_server.mapper.UserMapper;
import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/api")
public class UserController {

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
        result = new Result(0,"Success",Authority);
        return JSON.toJSONString(result);
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
}