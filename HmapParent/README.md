项目技术人员应此处描述一些项目基本信息，及注意事项：

* 1，使用的技术介绍
springmvc spring,mybatis,redis,mysql,spring aop

* 2，主要功能
*1)增加了inbound参数映射功能，如果对接的系统参数与前端传入的参数不同，需要做一定的处理或者映射，
那么可以在透传接口定义界面，定义处理该映射的工具类，将类的全名维护到接口定义中，中台会加载该映射类进行数据处理，将前端参数处理成
对接系统需要的数据类型。目前支持一套系统一种映射工具类，即同一类的接口会按照统一的方式进行处理。
*2）新增plsql方式透传，具体使用方法为，在接口配置表中将package.function名称配置在接口地址中如"hih_empployee_pkg.getEmpName"，此即为一个透传地址
其他字段配置方式同rest透传配置。代码中需要做的额外配置为，需要新建一个datasource指向对接系统,将JDBCSqlSessionFactory指向该datasoruce

* 3，配置信息

tomcat配置jndi
<Resource auth="Container" driverClassName="com.mysql.jdbc.Driver" url="jdbc:mysql://172.20.0.165:3306/hms_dev?useUnicode=true&amp;characterEncoding=UTF-8" name="jdbc/hms_dev" type="javax.sql.DataSource" username="root" password="123456"/>

*后端开发规范

1.所有表实体类在domain包中创建，并且实体类命名与表名相同，采用驼峰法命名
2.dto类创建在dto包中，末尾以DTO结尾
3.需要分页的查询在调用mapper之前，调用PageHelper.startPage(pageNum, pageSize);
4.所有需要分页查询的controller参数中使用PageRequest作为分页对象
5.所有controller需要继承BaseController
6.在controller之前增加@Timed注解（用于统计每个api的调用时间）
7.所有controller的url映射用/api开头，定义在@Controller注解下方
8.对于业务逻辑验证均在service中完成，对于验证例外不需要在controller中返回错误消息，需要报错告知前端采用抛出异常的方式，自定义异常需要继承BaseException，统一放于exception文件夹中可以参考HAP中的UserException。对于需要手工处理并记录日志的异常在catch块中一定要增加logger.error方法用于记录错误日志
9.自定义异常构造函数中code为模块简称如‘hms.interface’,descriptionKey参数为消息代码，消息需要维护在sys_prompt表中，
消息代码例如error.login.name_password_not_match，内容为'用户名或密码错误'，在当前系统中的维护路径为系统管理- 描述维护（后续界面重构后会使用新的界面进行维护）
移动中台错误代码规范为error.{code}.{错误描述},如'error.hms.login.name_password_not_match','error.hms.interface.cannot_find_url'
10.所有表的主键使用string类型并使用UUID生成主键
11.所有需要使用缓存的功能，需要在cache.impl包中定义缓存实现类。
12.所有建表语句采用单文件单表，并保存在core-db/tables文件夹中，表名均已hms_开头
