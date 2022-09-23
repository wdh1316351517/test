package mybatisdemo.demo.config.dds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Documentation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName : SwaggerConfig
 * @Description :
 * @Author : WDH
 * @Date: 2022/9/21  15:04
 **/

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    public Docket buildDocket(){
        /**
         * @description: 设置Docket 配置是否开启swagger 过滤 分组
         *               开发环境才会用到swagger
         * @param :
         * @return springfox.documentation.spring.web.plugins.Docket
         * @author: wdh
         * @date: 2022/9/21 15:43
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(ApiInfo()) //调用下面apiInfo()方法
                .select()
                .apis(RequestHandlerSelectors.basePackage("mybatisdemo.demo")) //注意路径
                .paths(PathSelectors.any())
                .build();
    }
    public ApiInfo ApiInfo(){
        return new ApiInfoBuilder()
                .title("swagger2 Api")
                .description("小型demo")
                .termsOfServiceUrl("http://www.163.com") //这里可以是项目地址
                .version("1.0.1")
                .build();
    }
}
