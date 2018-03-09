# CodeGenerator
    HAP 代码生成器 v1.0.0
    Author   : Jeb Sun
    数据库支持 : MySql Oracle
    BUG请提issue反馈，感谢使用！

[演示地址](https://jebsun.github.io/CodeGenerator/src/main/webapp/demo.html)

[可执行包下载](https://github.com/JebSun/CodeGenerator/blob/master/release/code-generator1.0.0-RELEASE.zip)

## 使用说明：<br/>
1. ##### 解压code-generator1.0.0-RELEASE.zip
2. ##### 在config目录下application.properties配置数据库连接, 如：

		spring.datasource.url=jdbc:mysql://127.0.0.1/hap_local?characterEncoding=utf8&useSSL=true
		spring.datasource.username=hap_local
		spring.datasource.password=hap_local
		spring.datasource.driver-class-name=com.mysql.jdbc.Driver
3. ##### 在config目录下application.properties修改tomcat端口、context-path, 如：

		server.port=8888
		server.context-path=/generator
4. ##### 执行Flat Jar

		java -jar code-generator-1.0.0-RELEASE.jar

5. ##### [http://localhost:8888/generator/](http://localhost:8888/generator/)

## 自定义模板：<br/>
* ##### 在config目录下template中存放freemarker代码模板
* ##### 数据模型说明Table

		String  schema                 //数据库schema
		String  tableName              //数据库表名，如 HAP_HR_EMPLOYEE
		String  className              //对应DTO名称，如 HapHrEmployee
		String  classNameFirstLowwer   //对应DTO名称，首字母小写，如 hapHrEmployee
		String  objectType             //数据库对象类型 "table" or "view"
		String  disableExtension       //是否禁用扩展字段
		String  basePackage            //基包名称，表名HAP_HR_EMPLOYEE，默认为hap.hr
		String  childSysName           //子模块名称，表名HAP_HR_EMPLOYEE，默认子模块名称为hr
		Boolean includeWho             //是否包含全部标准Who字段
		Boolean includeExtend          //是否包含全部扩展字段 ATTRIBUTE01 - ATTRIBUTE15
		Boolean includeOther           //是否包含BaseDTO其他字段 REQUEST_ID、PROGRAM_ID、OBJECT_VERSION_NUMBER
		String  stdHapTable            //是否HAP标准表，单主键，(includeWho == true && includeExtend == true)
		Column  primaryKeyColunm       //主键列，列对象定义见下节
		List<Column> columnList        //表、视图对象列对象list
		List<String> packageImports    //DTO需引入的包list

* ##### 数据模型说明Column

		String  columnName             //数据库字段名称, 如EMPLOYEE_ID
		String  columnDataBaseTypeName //数据库字段类型, 如VARCHAR2
		int     columnSize             //数据库字段长度, 如38
		String  columnComment          //数据库字段备注, 如'员工ID'
		String  defaultValue           //数据库字段默认值
		boolean primaryKey             //是否主键字段
		boolean nullable               //是否可空
		boolean unique                 //是否唯一
		String  javaProperty           //对应DTO属性名称, 如employeeId
		String  javaPropertyFirstUpper //对应DTO属性名称首字母大写, 如EmployeeId
		String  javaTypeName           //对应Java Type, 如String
		String  fullJavaTypeName       //对应Java Type全称, 如java.lang.String
		String  columnCategory         //字段类型 判断字段是否who字段'Hap-Who', HAP标准字段'Normal' ,WHO字段'Hap-Who',其他字段'Hap-Other'

