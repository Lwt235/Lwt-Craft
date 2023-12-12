package cn.lwt_server.service.impl;

import cn.lwt_server.mapper.BasicMapper;
import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.service.BasicService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BasicServiceBasic implements BasicService {

    @Autowired
    private BasicMapper basicMapper;

    @Override
    public String firstRequest() {
        Date date = new Date();
        Result result = new Result(0, "Success", date.toString());
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
//        System.out.println("检查：name: " + name + " password: " + password);
        Account account = basicMapper.checkAccount(name);
//        System.out.println("accout:"+account);
        if (account != null) {
//            System.out.println("返回: |" + account.getPassword() + "| ? |" + password + "|");
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
//                System.out.println(result);
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
}
