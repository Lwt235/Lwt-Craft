package cn.lwt_server.mapper;

import cn.lwt_server.pojo.FileMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    //上传文件后自动拓展文件列表
    int addFiles(String name, String url);

    //获取当前所有上传的文件
    List<FileMessage> getFileList();

    //删除指定文件
    int deleteFile(String url);
}
