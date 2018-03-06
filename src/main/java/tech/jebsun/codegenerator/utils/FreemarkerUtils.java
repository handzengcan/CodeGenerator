package tech.jebsun.codegenerator.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by JebSun on 2018/3/6.
 */
public class FreemarkerUtils {

    private String templatePath;

    private Configuration configuration; //模版配置对象

    private final Logger logger = LoggerFactory.getLogger(FreemarkerUtils.class);

    public void init() throws Exception {
        String classPath = this.getClass().getResource("/").getPath();

        //初始化FreeMarker配置
        //创建一个Configuration实例
        configuration = new Configuration(Configuration.VERSION_2_3_21);
        //设置FreeMarker的模版文件夹位置
        templatePath = classPath + "/template";


        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setDefaultEncoding("UTF-8");

    }

    public void process(Map map, String outPath, String className) throws Exception {
        File file = new File(templatePath);

        String[] templateFileNames = file.list();
        logger.info("===Begin Process :" + className + "===");
        for (int i = 0; i < templateFileNames.length; i++) {
            //判断是否为模板文件
            if (templateFileNames[i].lastIndexOf(".ftl") > 0) {
                //创建模版对象
                Template t = configuration.getTemplate(templateFileNames[i]);
                logger.info("===Process Template Begin:" + templateFileNames[i] + "===");
                //在模版上执行插值操作，并输出到制定的输出流中
                String outFileName = templateFileNames[i].replace("${className}", className).replace(".ftl", "");

                //获取输出路径
                String realOutPath = getOutPath(outPath, templateFileNames[i]);
                //判断输出文件夹是否存在不存在则创建
                File dir = new File(realOutPath);
                File parentDir = dir.getParentFile();

                if (!parentDir.exists() && !parentDir.isDirectory()) {
                    parentDir.mkdir();
                }
                if (!dir.exists() && !dir.isDirectory()) {
                    dir.mkdir();
                }

                File outputFile = new File(realOutPath + "/" + outFileName);
                Writer fileOut = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
                t.process(map, fileOut);
                fileOut.close();
                logger.info("===Process Template End:" + templateFileNames[i] + "===");
            }
        }
        logger.info("===End Process :" + className + "===");
    }

    public void process(Map map, String outPath, String className, String tableName) throws Exception {
        File file = new File(templatePath);

        String[] templateFileNames = file.list();
        logger.info("===Begin Process :" + className + "===");
        for (int i = 0; i < templateFileNames.length; i++) {
            //判断是否为模板文件
            if (templateFileNames[i].lastIndexOf(".ftl") > 0) {
                //创建模版对象
                Template t = configuration.getTemplate(templateFileNames[i]);
                logger.info("===Process Template Begin:" + templateFileNames[i] + "===");
                //在模版上执行插值操作，并输出到制定的输出流中
                String outFileName = templateFileNames[i]
                        .replace("${className}", className).replace("${tableName}", tableName).replace(".ftl", "");

                //获取输出路径
                String realOutPath = getOutPath(outPath, templateFileNames[i]);
                //判断输出文件夹是否存在不存在则创建
                File dir = new File(realOutPath);
                File parentDir = dir.getParentFile();

                if (!parentDir.exists() && !parentDir.isDirectory()) {
                    parentDir.mkdir();
                }
                if (!dir.exists() && !dir.isDirectory()) {
                    dir.mkdir();
                }

                File outputFile = new File(realOutPath + "/" + outFileName);
                Writer fileOut = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
                t.process(map, fileOut);
                fileOut.close();
                logger.info("===Process Template End:" + templateFileNames[i] + "===");
            }
        }
        logger.info("===End Process :" + className + "===");
    }

    private String getOutPath(String outPath, String templatefileName) {
        switch (templatefileName) {
            case "${className}.java.ftl":
                return outPath + "/dto";
            case "${className}Mapper.java.ftl":
                return outPath + "/mapper";
            case "I${className}Service.java.ftl":
                return outPath + "/service";
            case "${className}ServiceImpl.java.ftl":
                return outPath + "/service/impl";
            case "${className}Controller.java.ftl":
                return outPath + "/controllers";
            case "${className}ExtMapper.xml.ftl":
            case "${className}Mapper.xml.ftl":
                return outPath + "/mapperXml";
            default:
                return outPath;
        }
    }

}
