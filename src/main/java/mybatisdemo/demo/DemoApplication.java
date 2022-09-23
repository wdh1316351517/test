package mybatisdemo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName : DemoApplication
 * @Description :
 * @Author : WDH
 * @Date: 2022/9/21  15:08
 **/

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //禁止数据源自动配置
public class DemoApplication {
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class,args);
    }
}
