package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface UserMapper {

    //全查询
    public List<User> list();

    //删除
    public int delete(long id);

    //插入
    public int insert(User user);

    //更新
    public int update(User user);

    //id查询
    public User getById(long id);

    //条件查询
    public List<User> listBy(String msg, Timestamp startTime, Timestamp endTime);

    //批量删除
    public int deleteByIds(List<Long> ids);

    //密码校验
    public Account checkAccount(String name);
}