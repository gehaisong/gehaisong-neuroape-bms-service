package com.utechworld.bms.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.utechworld.neuroape.common.result.ResultJSON;
import com.utechworld.neuroape.service.UserDO;
import com.utechworld.neuroape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @RequestMapping(value = "refresh")
    public String refresh (HttpServletRequest request){
        refresh();
        return "refresh";
    }
    //    service服务
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
    /**
     * 刷新配置中心
     */
    public static void refresh() {

        HttpURLConnection connection =null;
        try {
            URL url = new URL("http://localhost:8763/refresh");//配置中心客户端刷新
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();//链接
            InputStream in=connection.getInputStream();//等待响应
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

    /**
     * 1.分页查询
     * @return
     */
    @RequestMapping(value = "users/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity getUserList (@PathVariable Integer pageNo, @PathVariable Integer pageSize){
        String search = "";
        JSONObject jsonObject  = userService.selectAll(pageNo,pageSize,search);
        return ResponseEntity.ok(jsonObject);
    }
    /**
     * 2.批量插入
     * @return
     */
    @RequestMapping(value = "add-all")
    public ResponseEntity<ResultJSON> addAll (){
        ResultJSON resultJSON = new ResultJSON();

        try {
            List<UserDO> list=new ArrayList<UserDO>();
            for(int i=0;i<3;i++){
                UserDO user=new UserDO();
                user.setUserId(92132385+i);
                user.setLibraryId(33333);
                user.setNickName("李批量插入"+i);
                user.setUserName("李22"+i);
                list.add(user);
            }
            List<Integer> idList=userService.insertAll(list);
            resultJSON.setData(idList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(resultJSON);
    }

    /** 3.插入 */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ResponseEntity<ResultJSON> add (UserDO user){
        ResultJSON resultJSON = new ResultJSON();
        user.setUserId(92132384);
        user.setLibraryId(33333);
        user.setNickName("李新东方用户");
        user.setUserName("李22");
        try {
            Integer id=userService.insert(user);
            user.setId(id);
            resultJSON.setData(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(resultJSON);
    }

    /** 4.主键查询 */
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResultJSON> getUserById (@PathVariable(value = "id") Integer id){
        ResultJSON resultJSON = new ResultJSON();
        try {
            UserDO user = userService.selectById(id);
            resultJSON.setData(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(resultJSON);
    }
    /** 6.更新对象 */
    @RequestMapping(value = "update/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResultJSON> update (@PathVariable(value = "userId") Integer userId){
        ResultJSON resultJSON = new ResultJSON();
        try {
            UserDO user = new UserDO();
            user.setUserId(userId);
            user.setNickName("李批量插入-更新");
            Integer num=userService.update(user);
            resultJSON.setData(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(resultJSON);
    }
    /** 7. 删除*/
    @RequestMapping(value = "delete")
    public ResponseEntity<ResultJSON> delete ( ){
        ResultJSON r = new ResultJSON();
        try {
            List<Integer> idList=new ArrayList<Integer>();
            idList.add(92132385);
            idList.add(92132386);
            Integer num= userService.delete(idList);
            r.setData(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
    @RequestMapping(value = "/redis")
    @ResponseBody
    public List<Integer> redisTest(){
        return userService.redisTest();
    }
}
