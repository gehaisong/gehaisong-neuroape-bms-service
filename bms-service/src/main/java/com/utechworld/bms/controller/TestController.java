package com.utechworld.bms.controller;

import com.github.pagehelper.PageInfo;
import com.utechworld.neuroape.common.result.Result;
import com.utechworld.neuroape.common.result.ResultPage;
import com.utechworld.neuroape.service.UserDO;
import com.utechworld.neuroape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gehaisong
 */
@RefreshScope
@RestController
@RequestMapping("/boot")
public class TestController {
    @Autowired
    private UserService userService;
    //获取注册中心配置
//    @Value("${test.host}")
//    String host_0;
    //获取注册中心配置
    @Value("${nacos.config.age}")
    String host;

    private String config;

    @RequestMapping("/getConfig")
    public String getConfig() {
        return this.config;
    }

    @RequestMapping(value = "index")
    public String index (HttpServletRequest request){

        return  "--d--"+host;
    }

    /**
     * 1.分页查询
     * @return
     */
    @RequestMapping(value = "users/{pageNo}/{pageSize}/{search}", method = RequestMethod.GET)
    public ResultPage getUserList (@PathVariable Integer pageNo, @PathVariable Integer pageSize,@PathVariable String search){
        PageInfo page = userService.selectAll(pageNo,pageSize,search);
        return ResultPage.ok(page.getList(),page.getPageNum(),page.getPageSize(),page.getTotal());
    }
    /**
     * 2.批量插入
     * @return
     */
    @RequestMapping(value = "add-all")
    public Result addAll (){
        try {
            List<UserDO> list=new ArrayList<UserDO>();
            for(int i=0;i<3;i++){
                UserDO user=new UserDO();
                user.setUserId(92132385+i);
                user.setLibraryId(33333);
                user.setNickName("nacos松"+i);
                user.setUserName("nacos松"+i);
                list.add(user);
            }
            List<Integer> idList=userService.insertAll(list);
           return Result.ok(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /** 3.插入 */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public Result add (UserDO user){
        user.setUserId(92132384);
        user.setLibraryId(33333);
        user.setNickName("李新东方用户");
        user.setUserName("李22");
        try {
            Integer id=userService.insert(user);
            user.setId(id);
            Result.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    /** 4.主键查询 */
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public Result getUserById (@PathVariable(value = "id") Integer id){
        try {
            UserDO user = userService.selectById(id);
            Result.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
    /** 6.更新对象 */
    @RequestMapping(value = "update/{userId}", method = RequestMethod.GET)
    public Result update (@PathVariable(value = "userId") Integer userId){
        try {
            UserDO user = new UserDO();
            user.setUserId(userId);
            user.setNickName("李批量插入-更新");
            Integer num=userService.update(user);
            Result.ok(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
    /** 7. 删除*/
    @RequestMapping(value = "delete")
    public Result delete ( ){
        try {
            List<Integer> idList=new ArrayList<Integer>();
            idList.add(92132385);
            idList.add(92132386);
            Integer num= userService.delete(idList);
            Result.ok(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
    @RequestMapping(value = "/redis")
    @ResponseBody
    public Result redisTest(){
        return Result.ok(userService.redisTest());
    }
}
