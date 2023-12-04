package cn.lwt_server.service.impl;

import cn.lwt_server.mapper.CommunityMapper;
import cn.lwt_server.pojo.Community;
import cn.lwt_server.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceBasic implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public List<Community> list() {
        return communityMapper.list();
    }
}
