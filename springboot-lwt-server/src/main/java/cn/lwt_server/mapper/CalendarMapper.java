package cn.lwt_server.mapper;

import cn.lwt_server.pojo.Calendar;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CalendarMapper {

    //全查询
    List<Calendar> list();

    //id查询
    Calendar getById(long id);

    //条件查询
    List<Calendar> listBy(String msg, Timestamp startTime, Timestamp endTime);

    //删除
    int delete(long id);

    //插入
    int insert(Calendar calendar);

    //更新
    int update(Calendar calendar);

    //批量删除
    int deleteByIds(List<Long> ids);
}