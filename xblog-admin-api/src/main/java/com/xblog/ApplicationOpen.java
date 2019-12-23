package com.xblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 *
 * blog 前台启动入口
 * @Author guodandan
 * @Date 2019/8/1 23:49
 */
@Slf4j
@SpringBootApplication
public class ApplicationOpen {

    public static void main(String[] args) throws Exception{

        SpringApplication app = new SpringApplication(ApplicationOpen.class);
        Environment env = app.run(args).getEnvironment();
        String port = env.getProperty("server.port");
        String netIp = InetAddress.getLocalHost().getHostAddress();
        String localIp = "127.0.0.1";
        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
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
        log.info("server starting ...");
    }
}
