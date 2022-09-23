package mybatisdemo.demo.config.dds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName : DynamicDataSourceAspect
 * @Description :
 * @Author : WDH
 * @Date: 2022/9/21  16:28
 **/
@Aspect
@Order(-1)  //该切面应当先于 @Transactional 执行
@Component
public class DynamicDataSourceAspect {
    /**
     * 切换数据源
     * @param point
     * @param dataSource
     */

    @Before("@annotation(dataSource)")
    public void switchDataSource(JoinPoint point,DataSource dataSource){
        //判断是否包含数据源
        if(!DynamicDataSourceContextHolder.containDataSourceKey(dataSource.value())){
            System.out.println("DataSource [{}] doesn't exist , use default DataSource [{}] "+dataSource.value());
        }else{
            //切换数据源
            DynamicDataSourceContextHolder.setDataSourceKey(dataSource.value());
            System.out.println("Switch DataSource to ["+DynamicDataSourceContextHolder.getDataSourceKey()+"] in Method"
                    +"] in Method ["+point.getSignature()+"]");
        }
    }
    /**
     * 重置数据源
     * @param point
     * @param dataSource
     */
    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point,DataSource dataSource){
        //将数据源置为默认数据源
        DynamicDataSourceContextHolder.clearDataSourceKey();
        System.out.println("Restore DataSource to [" + DynamicDataSourceContextHolder.getDataSourceKey()
        +"] in Method [ "+point.getSignature()+"]");
    }

}
