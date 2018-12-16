package com.rain.demo.service;

import com.rain.demo.dao.UserMapper;
import com.rain.demo.entity.User;
import com.rain.demo.entity.UserExample;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getUser(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andIsDeleteEqualTo("N");
        List<User> userList = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);

    }
}
