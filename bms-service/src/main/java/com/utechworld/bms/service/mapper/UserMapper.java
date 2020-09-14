package com.utechworld.bms.service.mapper;

import com.utechworld.neuroape.service.UserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by gehaisong
 *  @Mapper 给每个mapper单加注解 或 在application启动类 mapper扫描包
 *           @MapperScan("com.koolearn.**.mapper")
 *  所以统一配置@MapperScan在扫描路径在application启动类中
 *  1。@Param:给方法参数取名 一个参数的情况下，可以不使用@Param
 *  2。#{} 的作用主要是替换预编译语句(PrepareStatement)中的占位符?
 *     ${} 符号的作用是直接进行字符串替换
 *
 * @CacheNamespace： 使用二级缓存 -- SpringBoot中默认全局开启了二级缓存但不使用
 *    缓存范围是当前mapper，当前mapper执行更新操作时，二级缓存重新加载
 *
 *    增删改查：@Insert、@Update、@Delete、@Select、@MapKey、@Options、@SelelctKey、@Param、@InsertProvider、@UpdateProvider、@DeleteProvider、@SelectProvider

     结果集映射：@Results、@Result、@ResultMap、@ResultType、@ConstructorArgs、@Arg、@One、@Many、@TypeDiscriminator、@Case

     缓存：@CacheNamespace、@Property、@CacheNamespaceRef、@Flush

       绝大部分注解，在xml映射文件中都有元素与之对应，但是不是所有。此外在mybatis-spring中提供了@Mapper注解和@MapperScan注解，用于和spring进行整合
     参数使用了@Param注解的情况下，依然根据参数位置进行引用(param1，param2…，下标从1开始)
 *
 */
@Service
@Mapper
@CacheNamespace
public interface UserMapper {
    /** 1. 动态sql-批量插入 返回自增主键， ID封装在对象id属性中 */
    @InsertProvider(type = UserMapperDynaSqlProvider.class, method = "insertAll")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insertAll(List<UserDO> userDOList);

    /**
     *
     * 2.1 动态sql-分页查询 id=true 表示主键
     *    sql中字段名和属性名相同可以省略 @Result   userId
     *    @Results 设置id， 通过@ResultMap 进行复用
     *      @Results 对应 <resultMap></resultMap>标签
     *      @Result  对应 <result></result>标签
     */
    @Results(id = "userResultsMap",value = {
            @Result(id=true,property="id",column="id"),
            @Result(property="nickName",column="nick_name"),
            @Result(property="userName",column="user_name"),
            @Result(property="headImg",column="head_img")
    })
    @SelectProvider(type=UserMapperDynaSqlProvider.class,method="selectAllDynaSql")
    List<UserDO> selectAllDynaSql(String searchContent);

    /** 2.2 动态sql-分页查询- 测试 @Results复用
     * @ResultMap 复用 id=userResultsMap 的 @Results
     *  可以是xml定义的
     * @param searchContent
     * @return
     */
    @ResultMap("userResultsMap")
    @SelectProvider(type=UserMapperDynaSqlProvider.class,method="selectAllDynaSql")
    List<UserDO> selectAllDynaSqlResultMap(String searchContent);

    /**
     * 2.3 动态sql-分页查询- 测试 @MapKey - 集合返回map形式
     * @param searchContent
     * @return
     */
    @ResultMap("userResultsMap")
    @SelectProvider(type=UserMapperDynaSqlProvider.class,method="selectAllDynaSql")
    @MapKey("userId")
//    @Options(useCache = false)//此方法不使用二级缓存
    Map<Integer,UserDO> selectAllDynaSqlMap(String searchContent);

    /**
     * 3.插入操作，返回自增主键， ID封装在对象id属性中
     *
     * @param personDO
     * @return 记录数
     */
    @Insert("insert into sy_reading_user(user_id ,library_id,user_name,nick_name) " +
            " values(#{userId},#{libraryId},#{userName},#{nickName})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insert(UserDO personDO);


    /** 4.主键查询 */
    @Select("select id,user_id userId,library_id,user_name,nick_name,head_img " +
            " from sy_reading_user where id=#{id}")
    @ResultMap("userResultsMap")
    UserDO selectById(@Param("id") Integer id);

    /** 5.更新操作
     * @Update("update user_copy set name=#{name} where id=#{id}")
     * */
    @UpdateProvider(type = UserMapperDynaSqlProvider.class,method = "update")
    Integer update(UserDO personDO);

    /** 6.删除操作 */
    @DeleteProvider(type = UserMapperDynaSqlProvider.class,method = "deleteByIdList")
    Integer delete(List<Integer> idList);


    /**
     *  @Select 动态sql-2
     *   结果属性一对一映射 @Result(property="address",column="address_id",one=@One(select="mapper方法"))
     *   结果属性一对多映射 @Result(property="students",column="address_id",many=@Many(select="mapper方法"))
     * @return
     */
    @Select("<script>" +
            " select id,name,age from user_copy where 1=1 " +
            "   <if test='name!=null'> " +
            "     and name like #{name} " +
            "   </if>" +
            " </script>"  )
    @Results({
            @Result(id=true,property="id",column="id"),
            @Result(property="name",column="name"),
            @Result(property="age",column="age")
    })
    List<UserDO> selectAll(@Param("name") String name);

    /**
     * <--开启本mapper的namespace下的二级缓存，
     *  二级缓存是基于 mapper文件的namespace的，也就是说多个sqlSession可以共享一个mapper中的二级缓存区域-->
         eviction:代表的是缓存回收策略，目前MyBatis提供以下策略。
             (1) LRU,最近最少使用的，一处最长时间不用的对象
             (2) FIFO,先进先出，按对象进入缓存的顺序来移除他们
             (3) SOFT,软引用，移除基于垃圾回收器状态和软引用规则的对象
             (4) WEAK,弱引用，更积极的移除基于垃圾收集器状态和弱引用规则的对象。这里采用的是LRU，
                移除最长时间不用的对形象

         flushInterval:刷新间隔时间，单位为毫秒，这里配置的是100秒刷新，如果你不配置它，那么当SQL被执行的时候才会去刷新缓存。

         size:引用数目，一个正整数，代表缓存最多可以存储多少个对象，不宜设置过大。设置过大会导致内存溢出。这里配置的是1024个对象

         readOnly:只读，意味着缓存数据只能读取而不能修改，这样设置的好处是我们可以快速读取缓存，缺点是我们没有
         办法修改缓存，他的默认值是false，不允许我们修改
         <cache eviction="LRU" flushInterval="100000" readOnly="true" size="1024"/>
             SpringBoot中默认帮我们全局开启了二级缓存，如果想要使用二级缓存还需要在mapper上注明
           @CacheNamespace注解-- 对应xml<cache>  ，在该mapper上使用二级缓存
     */

    //1、根据文章id查询文章Article对象，同时通过One注解关联查询出作者Author信息
    @Select("SELECT id,title,content,author_id FROM article where id= #{articleId}")
    @Results(id = "articleWithAuthor", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            //property属性：指定将关联查询的结果封装到Article对象的author属性上
            //column属性指定：指定在执行@One注解中定义的select语句时，把article表的author_id字段当做参数传入
            //one属性：通过@One注解定义关联查询的语句是AuthorMapper中的findAuthorByAuthorId方法
            @Result(property = "author",column = "author_id",
                    one = @One(select = "com.tianshouzhi.mapper.AuthorMapper.findAuthorByAuthorId"))
    })
    public Object findArticleWithAuthorByArticleId(@Param("articleId") int articleId);

    //根据作者id查询Author信息，通过@Many注解关联查询出所有的文章信息
    @Select("SELECT id,name FROM author WHERE id=#{authorId}")
    @Results(id = "authorWithArticles", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
                    //property属性：指定将关联查询的结果封装到Author对象的articles属性上
                    //column属性指定：指定在执行@Many注解中定义的select语句时，把author表的id字段当做参数传入
                    //many属性：指定通过@Many注解定义关联查询的语句是ArticleMapper中的findArticlesByAuthorId方法
                    @Result(property = "articles",column = "id",
                    many = @Many(select = "com.tianshouzhi.mapper.ArticleMapper.findArticlesByAuthorId"))
    })
    Object findAuthorWithArticlesByAuthorId(int authorId);

}