package cn.lwt_server.service;

import java.sql.Timestamp;
import java.util.List;

public interface CalendarService {
    String getCalendar();
    String getById(long id);
    String listBy(String msg, Timestamp startTime, Timestamp endTime);
    String insert(String jwt, String msg, String startTime, String endTime);
    String update(String jwt, long id, String msg, Timestamp startTime, Timestamp endTime);
    String delete(String jwt, long id);
    String deleteByIds(String jwt, List<Long> ids);
}
