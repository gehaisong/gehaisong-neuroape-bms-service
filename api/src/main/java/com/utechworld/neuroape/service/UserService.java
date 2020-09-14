package com.utechworld.neuroape.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

//import org.springframework.stereotype.Service;

/**
 * Created by gehaisong
 */
//@Service
public interface UserService {
    public Integer insert(UserDO personDO);
    public Integer update(UserDO personDO);
    public  Integer delete(List<Integer> idList);
    public UserDO selectById(Integer id);
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    JSONObject selectAll(Integer page, Integer pageSize, String search);

    /**
     * 批量插入
     * @param userDOList
     * @return
     */
    List<Integer> insertAll(List<UserDO> userDOList);

    List<Integer> redisTest();
}
