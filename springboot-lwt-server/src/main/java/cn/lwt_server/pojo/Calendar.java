package cn.lwt_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

    private long id;
    private String msg;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp editTime;

    /*public User() {
    }

    public User(BigInteger id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", startTime='" + startTime.toString().substring(0, 19) + '\'' +
                ", endTime='" + endTime.toString().substring(0, 19) + '\'' +
                ", editTime='" + editTime.toString().substring(0, 19) + '\'' +
                '}';
    }
}
