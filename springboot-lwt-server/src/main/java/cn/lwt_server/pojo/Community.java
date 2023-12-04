package cn.lwt_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private int id;
    private String nickname;
    private String avatarPath;
    private String detailed_information;
    private String link_path;
}
