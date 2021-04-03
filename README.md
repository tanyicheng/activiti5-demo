# activiti5-demo

#第一章：Activiti 简介 

    百度百科：Activiti Activiti 
    官方主页：http://www.activiti.org/ 
    用户指南：http://www.activiti.org/userguide/ 
Activiti HelloWorld 实现 

    1，Activiti 的 25 张表； 
    2，引入 Activiti 配置文件 activiti.cfg.xml； 
    3，在 Eclipse 上安装 Activiti 插件； 
    4，初识 Activiti 流程设计工具；
    5，了解 Activiti bpmn 图表对应的 XML 文件
    6，Activiti HelloWorld 实现（代码层次） 
    7，Activiti HelloWorld 实现（表数据发生的变化）

#第二章 Activiti 流程定义  

    第一节：流程定义添加（部署） Classpath 加载方式； Zip 加载方式；
    第二节：流程定义查询 查询流程定义； 
            查询某个流程定义的流程设计图片； 
            查询最新版本的流程定义集合；
    第三节：流程定义删除 删除 key 相同的所有流程定义
    第四节：流程定义的‘修改’

#第三章 Activiti 流程实例  
    
    第一节：构建学生请假审批流程 
    第二节：执行对象概念 
    第三节：判断流程实例状态 
    第四节：历史流程实例查询 
    第五节：历史活动查询

#第四章 Activiti 流程变量  
    第一节：流程变量的概念 
    第二节：使用 TaskService 设置和获取流程变量 
    第三节：局部流程变量 
    第四节：使用 RuntimeService 设置和获取流程变量 
    第五节：启动流程的时候设置流程变量 
    第六节：完成任务的时候设置流程变量

#第五章 Activiti 流程控制网关  
    第一节：连线 
    第二节：排它网关 
    第三节：并行网关

#第六章 Activiti 任务分配  
第一节：个人任务分配 

    方式一：直接流程图配置中写死； 
    方式二：使用流程变量； 
    方式三：TaskListener 监听实现； 

第二节：多用户任务分配 

    方式一：直接流程图配置中写死； 
    方式二：使用流程变量； 
    方式三：TaskListener 监听实现； 

第三节：组任务分配 Activiti 之内置用户组设计表以及 IdentityService 

    方式一：直接流程图配置中写死； 
    方式二：使用流程变量；