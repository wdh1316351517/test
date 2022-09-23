package mybatisdemo.demo.config.dds;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : mybatIsConfig
 * @Description :
 * @Author : WDH
 * @Date: 2022/9/21  15:04
 **/

@Configuration
@MapperScan("mybatisdemo.demo.**.dao")  //扫描Dao
public class MybatIsConfig {

    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master(){
        return DataSourceBuilder.create().build();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slave(){
        return DataSourceBuilder.create().build();
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object>  dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("master",master());
        dataSourceMap.put("slave",slave());
        //将master数据源作为默认指定的数据源
        dynamicDataSource.setDefaultDataSource(master());
        //将master 和 slave 数据源作为指定的数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        //配置数据源，此处配置为关键配置 如果没有将dynamicDataSource作为数据源 则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        sessionFactory.setTypeAliasesPackage("mybatisdemo.demo.**.model"); //扫描model
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/sqlmap/*.xml")); //扫描映射文件
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        //配置事务管理 使用事务时 在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }


}
