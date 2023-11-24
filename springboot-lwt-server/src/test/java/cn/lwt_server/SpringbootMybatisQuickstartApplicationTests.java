package cn.lwt_server;

import cn.lwt_server.mapper.UserMapper;
import cn.lwt_server.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.*;

//@SpringBootTest
class SpringbootMybatisQuickstartApplicationTests {

//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testDelete() {
//        int deleteStatus = userMapper.delete(1697710149999L);
//        System.out.println(deleteStatus);
//    }
//
//    @Test
//    public void testUpdate() {
//        User user = new User();
//        user.setId(1699921304341L);
//        user.setMsg("test_1");
//        user.setEditTime(new Timestamp(System.currentTimeMillis()));
//        int updateStatus = userMapper.update(user);
//        System.out.println(updateStatus);
//    }
//
//    @Test
//    public void testGetById() {
//        User user = userMapper.getById(1699921304341L);
//        System.out.println(user);
//    }
//
//    //Timestamp.valueOf("2023-11-14 10:00:00"),Timestamp.valueOf("2023-11-15 11:00:00")
//
//    @Test
//    public void testListBy() {
//        List<User> userList = userMapper.listBy(null, null, null);
//        userList.stream().forEach((user) -> {
//            System.out.println(user);
//        });
//    }
//
//    @Test
//    public void testDeleteByIds() {
//        List<Long> ids = Arrays.asList(1699921304341L, 1699933920466L);
//        int deleteByIdsStatues = userMapper.deleteByIds(ids);
//        System.out.println(deleteByIdsStatues);
//    }

//    @Test
//    public void testGenJwt() {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("id",1);
//        claims.put("name","tom");
//        String secretkey="cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
//
//
//        String jwt = Jwts.builder()
//                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretkey)), SignatureAlgorithm.HS256)
//                .setClaims(claims)
//                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
//                .compact();
//        System.out.println(jwt);
//    }
//
//    @Test
//    public void testParser() {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
//                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcwMDcwOTgzN30.8el0XTbZAVPr0V6-CY93-DPsHX8uxKqeew_RdfEr1Xs")
//                .getBody();
//        System.out.println(claims);
//    }
}
