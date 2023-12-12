package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Account;
import cn.lwt_server.pojo.Mail;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface MailMapper {

    //检查验证码是否存在
    List<Mail> getByAddress(String address);

    //插入验证码信息
    int insert(String email, String verifyCode, Timestamp time);

    //删除验证码信息
    int delete(String address);

    //检查邮箱是否重复
    List<Account> isRepeat(String email);

    //添加账号
    int addAccount(String name, String password, String authority, String emailAddress);

    //修改密码
    int editAccount(String email, String password);
}
