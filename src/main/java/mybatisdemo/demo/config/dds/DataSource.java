package mybatisdemo.demo.config.dds;

/**
 * @ClassName : DataSource
 * @Description : 配置动态注解，用于数据源标识
 * @Author : WDH
 * @Date: 2022/9/21  15:54
 **/

import java.lang.annotation.*;

/**
 * 动态数据源注解
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /**
     * 数据源key值
     * @return
     */
    String value();

}
