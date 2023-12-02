package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.FileMessage;
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

    //上传文件后自动拓展文件列表
    public int addFiles(String name, String url);

    //获取当前所有上传的文件
    public List<FileMessage> getFileList();

    //删除指定文件
    public int deleteFile(String url);
}