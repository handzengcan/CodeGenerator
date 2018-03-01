package tech.jebsun.codegenerator.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.factory.GeneratorServiceFactory;
import tech.jebsun.codegenerator.service.IGeneratorService;
import tech.jebsun.codegenerator.service.impl.MysqlGeneratorServiceImpl;
import tech.jebsun.codegenerator.service.impl.OracleGeneratorServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JebSun on 2018/2/28.
 */
@Configurable
public class SpringConfiguration {

    @Bean
    public GeneratorServiceFactory generatorServiceFactory(MysqlGeneratorServiceImpl mysqlGeneratorService
            , OracleGeneratorServiceImpl oracleGeneratorService) {

        GeneratorServiceFactory factory = new GeneratorServiceFactory();
        Map<DataBaseTypeEnum, IGeneratorService> serviceMap = new HashMap<>();
        serviceMap.put(DataBaseTypeEnum.MYSQL, mysqlGeneratorService);
        serviceMap.put(DataBaseTypeEnum.ORACLE, oracleGeneratorService);
        factory.setGeneratorServiceMap(serviceMap);

        return factory;
    }

}
