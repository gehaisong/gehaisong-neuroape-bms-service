package com.utechworld.bms.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.utechworld.bms.common.redis.RedisUtils;
import com.utechworld.bms.service.mapper.UserMapper;
import com.utechworld.neuroape.service.UserDO;
import com.utechworld.neuroape.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gehaisong

 */
@EnableTransactionManagement  // 需要事务的时候加上
@Service
public class UserServiceImpl implements UserService,Serializable{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;//测试redis

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Integer insert(UserDO personDO) {
          userMapper.insert(personDO);
          return personDO.getId();
    }

    @Override
    public List<Integer> insertAll(List<UserDO> userDOList) {
        List<Integer> idList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userDOList)){
            int num =  userMapper.insertAll(userDOList);
            if(num>0){
                for (UserDO userDO : userDOList) {
                    idList.add(userDO.getId());
                }
            }
        }
        return idList;
    }

    @Override
    public Integer update(UserDO personDO) {
        return userMapper.update(personDO);
    }

    @Override
    public Integer delete(List<Integer> idList) {
        return userMapper.delete(idList);
    }

    @Override
    public UserDO selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public PageInfo selectAll(Integer pageNo, Integer pageSize, String search) {
        try {
            if(StringUtils.isNotBlank(search)){
                search = "%"+search.trim()+"%";
            }
            String key ="selet-all-page-pageNo="+pageNo+"pageSize="+pageSize+"search="+search;
            PageInfo pageInfo = (PageInfo) redisUtils.get(key);
            if(pageInfo == null){
                //使用分页插件,核心代码就这一行 #分页配置#
                Page page = PageHelper.startPage(pageNo, pageSize);
                List<UserDO> list = userMapper.selectAllDynaSqlResultMap(search);
                pageInfo = new PageInfo(list);
                redisUtils.set(key,pageInfo,10);
            }
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public List<Integer> redisTest(){
        List<Integer> users=new ArrayList<>();
        Random random =new Random();
        users.add(random.nextInt());
        users.add(122334);
        redisUtils.set("list",users);
        redisUtils.set("name","ghs");

        String name = (String) redisUtils.get("name");

        List<Integer> list= (List<Integer>) redisUtils.get("list");
        return list;
    }
}
