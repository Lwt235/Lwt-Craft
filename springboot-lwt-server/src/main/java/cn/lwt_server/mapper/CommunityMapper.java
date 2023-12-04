package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
    //获取全部数据
    List<Community> list();
}
