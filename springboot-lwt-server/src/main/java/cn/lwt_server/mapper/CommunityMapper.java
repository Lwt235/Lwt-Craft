package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Calendar;
import cn.lwt_server.pojo.Community;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
    //获取全部数据
    List<Community> list();

    //id查询
    Community getById(Integer id);

    //插入
    int insert(Community community);

    //更新
    int update(Community community);
}
