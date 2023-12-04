package cn.lwt_server.service.impl;

import cn.lwt_server.mapper.CommunityMapper;
import cn.lwt_server.pojo.Community;
import cn.lwt_server.pojo.Result;
import cn.lwt_server.service.CommunityService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceBasic implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public String list() {
        List<Community> communityList = communityMapper.list();
        System.out.println(communityList);
        Result result = new Result(0, "Success", JSON.toJSONString(communityList));
        return JSON.toJSONString(result);
    }
}
