im后台管理系统
项目结构
 1.im-admin-api
   用于提供外部接口
 2.im-admin-manage
   用于后台管理
 3.im-admin-dao
  用于数据库操作与配置
 4.im-admin-common
   通用代码 例如util包,redis配置等   
1.0版本
  ---简单的后台登录
  ---管理回调通知url
约定:
maven的依赖版本均在父级dependencyManagement pom中进行维护
以利于版本的统一 
  
  