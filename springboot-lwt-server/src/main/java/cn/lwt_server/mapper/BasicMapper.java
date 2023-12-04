package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BasicMapper {

    //密码校验
    Account checkAccount(String name);
}
