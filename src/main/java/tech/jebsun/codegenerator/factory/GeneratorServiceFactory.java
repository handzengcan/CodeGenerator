package tech.jebsun.codegenerator.factory;

import tech.jebsun.codegenerator.enums.DataBaseTypeEnum;
import tech.jebsun.codegenerator.service.IGeneratorService;

import java.util.Map;

/**
 * 代码生成器服务工厂
 * Created by JebSun on 2018/2/28.
 */
public class GeneratorServiceFactory {

    private Map<DataBaseTypeEnum, IGeneratorService> generatorServiceMap;

    public Map<DataBaseTypeEnum, IGeneratorService> getGeneratorServiceMap() {
        return generatorServiceMap;
    }

    public void setGeneratorServiceMap(Map<DataBaseTypeEnum, IGeneratorService> generatorServiceMap) {
        this.generatorServiceMap = generatorServiceMap;
    }

    public IGeneratorService getGeneratorService(DataBaseTypeEnum dataBaseType) {
        if(generatorServiceMap.containsKey(dataBaseType))
            return generatorServiceMap.get(dataBaseType);
        return null;
    }
}
