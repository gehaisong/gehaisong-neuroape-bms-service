package com.utechworld.bms.common.test;

import com.utechworld.neuroape.service.UserDO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gehaisong
 * @Configuration: spring boot才会扫描到该配置。该注解类似于之前使用xml进行配置
 *      被注解的类内部包含有一个或多个被@Bean注解的方法，用于构建bean定义，初始化Spring容器
 *       @Bean注解的方法 会被AnnotationConfigApplicationContext
 *                     或AnnotationConfigWebApplicationContext类进行扫描
 *
 *  bean：@Primary	优先方案，被注解的实现，优先被注入
         @Qualifier	先声明后使用，相当于多个实现起多个不同的名字，注入指定bean名
   参考数据源注解配置：com.koolearn.boottest.common.config.DruidDBConfig
 */
@Configuration
@ComponentScan(basePackages = "com.utechworld")
public class TestZhujie {
    public TestZhujie(){
        //@Configuration标注在类上，配置spring容器(应用上下文),相当于把该类作为spring的xml配置文件中的<beans>，
        System.out.println("TestZhujie容器初始化...");
    }

    /**
     *  第一种方式注册bean： @Configuration启动容器 + @Bean注册 Bean
     *
     *  1.@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象
     *  2.不指定bean名称，默认和方法名相同
     *  3.默认作用域为单例singleton作用域 ,@Scope(“prototype”)设置为原型作用域
     * @return
     * LOG_PATH_IS_UNDEFINED
     */
    @Bean
    public UserDO userDO(){
          //此方法等价于 <bean>标签，给UserDO注册一个bean对象
            return new UserDO();
    }

    /** 第二种方式注册bean：
     *      1.@Configuration启动容器 + @Component 注册Bean
     *      2.在容器类上添加指定扫描注解：
     *         @ComponentScan(basePackages = "com.koolearn.boottest")
     *      3.用  @Component 标注 bean类
     */



    public void test(){
        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(TestZhujie.class);
    }
}
