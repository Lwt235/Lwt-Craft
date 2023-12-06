package cn.lwt_server.service.impl;

import cn.lwt_server.mapper.CalendarMapper;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.pojo.Calendar;
import cn.lwt_server.service.CalendarService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CalendarServiceBasic implements CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;

    @Override
    public String getCalendar() {
        List<Calendar> calendarList = calendarMapper.list();
        Result result = new Result(0, "Success", JSON.toJSONString(calendarList));
        return JSON.toJSONString(result);
    }

    @Override
    public String getById(long id) {
        Calendar calendar = calendarMapper.getById(id);
        System.out.println(calendar);
        Result result = new Result(0, "Success", JSON.toJSONString(calendar));
        return JSON.toJSONString(result);
    }

    @Override
    public String listBy(String msg, Timestamp startTime, Timestamp endTime) {
        List<Calendar> calendarList = calendarMapper.listBy(msg, startTime, endTime);
        Result result = new Result(0, "Success", JSON.toJSONString(calendarList));
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
            Calendar newCalendar = new Calendar(System.currentTimeMillis(), msg, Timestamp.valueOf(startTime), Timestamp.valueOf(endTime), new Timestamp(System.currentTimeMillis()));
            if (newCalendar.getStartTime().after(newCalendar.getEndTime())) {
                result = new Result(2, "Error: The endTime is earlier than startTime", null);
            } else {
                calendarMapper.insert(newCalendar);
                result = new Result(0, "success in insert one item", newCalendar.toString());
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
            calendarMapper.update(new Calendar(id, msg, startTime, endTime, new Timestamp(System.currentTimeMillis())));
            Calendar calendar = calendarMapper.getById(id);
            result = new Result(0, "success in update item:", calendar.toString());
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
