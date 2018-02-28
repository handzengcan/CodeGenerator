package tech.jebsun.codegenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jebsun.codegenerator.service.IGeneratorService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by JebSun on 2018/2/28.
 */
@RestController
public class TestController {

    @Autowired
    IGeneratorService generatorService;

    @RequestMapping
    public String index() throws SQLException {
        //return "Hello Spring Boot!";
        List<Map<String,String>> shcemaList = generatorService.getAllSchemas();
        StringBuilder sb = new StringBuilder();

        for(Map<String,String> shcemaMap : shcemaList) {
            sb.append("schema name: ").append(shcemaMap.get("name")).append("\n")
                    .append("schema value: ").append(shcemaMap.get("value"));
        }

        return sb.toString();
    }
}
