package cn.lwt_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private String time;
}
