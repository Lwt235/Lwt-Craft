package cn.lwt_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private int id;
    private String title;
    private String content;
    private int userId;
    private String time;
}
