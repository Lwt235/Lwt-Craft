package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.FileMessage;
import cn.lwt_server.pojo.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CalendarMapper {

    //全查询
    List<User> list();

    //删除
    int delete(long id);

    //插入
    int insert(User user);

    //更新
    int update(User user);

    //id查询
    User getById(long id);

    //条件查询
    List<User> listBy(String msg, Timestamp startTime, Timestamp endTime);

    //批量删除
    int deleteByIds(List<Long> ids);

    //密码校验
    Account checkAccount(String name);
}