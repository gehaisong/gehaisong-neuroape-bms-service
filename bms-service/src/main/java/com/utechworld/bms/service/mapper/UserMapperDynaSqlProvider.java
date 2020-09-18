package com.utechworld.bms.service.mapper;


import com.utechworld.neuroape.service.UserDO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 *  单个参数：使用#{参数名} 取值，mybatis没做特殊处理
    多个参数：使用#{param1}，#{param2}取值
    命名参数：通过@param("key")明确指定封装map中的key，通过#{key}取值

    POJO：如果多个参数正好是业务模型，这时候就可以传入业务模型，通过#{属性名} 取值
    Map：通过 #{key}取值 , 列：in ( #{list} ) ，默认的key是”list”
         如果多个参数不是业务模型，而且不经常使用，可以自定义Map传入
    TO：如果多个参数不是业务模型，而且经常使用，可以自定义一个TO来传输对象

 */
public class UserMapperDynaSqlProvider {
    /**
     * 批量插入
     * 1.MyBatis会把UserMapper的insertAll方法中的List类型的参数存入一个Map中, 默认的key是”list”,
     * 2.可以用@Param注解自定义名称
     * 3.MyBatis在调用@InsertProvide指定的方法时将此map作为参数传入
     * 4.所有代码中使用List users = (List) map.get(“list”);获取list参数
     *
     * #{list[0].name}就表示从List参数的取第0个元素的name的值了, “list”跟key是对应的
     * @param map
     * @return
     */
    public String insertAll(Map map) {
        List<UserDO> insertList = (List<UserDO>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sy_reading_user ");
        sb.append("(user_id ,user_name,nick_name) ");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat(
                "( #'{'list[{0}].userId},#'{'list[{0}].userName},#'{'list[{0}].nickName} )");
        for (int i = 0; i < insertList.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < insertList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 动态sql：列表分页查询
     * @param searchContent
     * @return
     */
    public String selectAllDynaSql(final String searchContent){
        SQL sql = new SQL(){
            {
                SELECT(" id,user_id userId,user_name,nick_name,head_img ");
                FROM("sy_reading_user ");
                if(!StringUtils.isEmpty(searchContent)){
                    WHERE(" nick_name like #{searchContent}");
                }
                ORDER_BY("user_id desc");
            }
        };
        return sql.toString();
    }

    /** 修改对象*/
    public String update(final UserDO userDO){
        return new SQL(){
            {
                UPDATE("sy_reading_user");
                if(!StringUtils.isEmpty(userDO.getNickName())){
                    SET("nick_name=#{nickName}");
                }

                WHERE("user_id=#{userId}");
            }
        }.toString();
    }

    /**
     * 删除 参数参考 批量插入
     * @return
     */
    public String deleteByIdList(Map map){
        String sql= new SQL(){
            {
                DELETE_FROM("sy_reading_user");
                WHERE("user_id in ( #{list} ) ");
            }
        }.toString();
        return sql;
    }

    private String selectPersonSql() {
        return new SQL() {{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
            SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
            FROM("PERSON P");
            FROM("ACCOUNT A");
            INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
            INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
            WHERE("P.ID = A.ID");
            WHERE("P.FIRST_NAME like ?");
            OR();
            WHERE("P.LAST_NAME like ?");
            GROUP_BY("P.ID");
            HAVING("P.LAST_NAME like ?");
            OR();
            HAVING("P.FIRST_NAME like ?");
            ORDER_BY("P.ID");
            ORDER_BY("P.FULL_NAME");
        }}.toString();
    }
}
