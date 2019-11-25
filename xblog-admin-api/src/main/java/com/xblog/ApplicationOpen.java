package com.xblog;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 *
 * blog 前台启动入口
 * @Author guodandan
 * @Date 2019/8/1 23:49
 */
@SpringBootApplication
public class ApplicationOpen {

    public static Logger logger = LoggerFactory.getLogger(ApplicationOpen.class);

    public static void main(String[] args) throws Exception{
        SpringApplication app = new SpringApplication(ApplicationOpen.class);
        Environment env = app.run(args).getEnvironment();
        String port = env.getProperty("server.port");
        String netIp = InetAddress.getLocalHost().getHostAddress();
        String localIp = "127.0.0.1";
        logger.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Blog: \t\t http://{}:{}/portal/index/ \n\t" +
                        "Blog Admin:\t http://{}:{}/admin/index/ \n\t" +
                        "Swagger: \t http://{}:{}/swagger-ui.html \n\t" +
                        "Local: \t\t http://{}:{} \n\t" +
                        "External: \t http://{}:{} \n----------------------------------------------------------",
                localIp,port,
                localIp,port,
                localIp,port,
                localIp,port,
                netIp,port);
        logger.info("server starting ...");
    }

    /**
     * 使用@Bean注解注入第三方的解析框架（fastJson）
     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters(){
//        //1、首先需要先定义一个convert转换消息对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //2、添加fastJson的配置信息，比如：是否要格式化返回的json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        //3、在convert中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        return new HttpMessageConverters(fastConverter);
//    }
}
