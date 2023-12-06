package cn.lwt_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private Integer id;
    private String nickname;
    private String avatarPath;
    private String title;
    private String detailedInformation;
    private String linkPath;
    private Timestamp time;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", title='" + title + '\'' +
                ", detailedInformation='" + detailedInformation + '\'' +
                ", linkPath='" + linkPath + '\'' +
                ", time='" + time.toString().substring(0, 19) + '\'' +
                '}';
    }
}
