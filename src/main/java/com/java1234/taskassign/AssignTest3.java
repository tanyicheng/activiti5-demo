package com.java1234.taskassign;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * course-14 设置审批人 TaskListener 监听实现；
 *
 * @author created by barrett in 2021/4/2 22:31
 **/
public class AssignTest3 {

    /**
     * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
     */
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deploy() {
        Deployment deployment = processEngine.getRepositoryService() // 获取部署相关Service
                .createDeployment() // 创建部署
                .addClasspathResource("diagrams/StudentLeaveProcess7.bpmn") // 加载资源文件
                .addClasspathResource("diagrams/StudentLeaveProcess7.png") // 加载资源文件
                .name("学生请假流程7") // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());

        start2();
    }

    @Test
    public void start2() {
        //增加监听器
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessleaveTaskListener", new BusinessleaveTaskListener());
        map.put("user", "mpc");

        ProcessInstance pi = processEngine.getRuntimeService() // 运行时Service
                .startProcessInstanceByKey("studentLevaeProcess7", map); // 流程定义表的KEY字段值
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());

        //查询任务
        TaskService taskService = processEngine.getTaskService();
        Task task = processEngine.getTaskService() // 任务相关Service
                .createTaskQuery() // 创建任务查询
                .processInstanceId(pi.getProcessDefinitionId())//流程实例
                .singleResult();

        //完成任务
        taskService.complete(task.getId());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void start() {
        ProcessInstance pi = processEngine.getRuntimeService() // 运行时Service
                .startProcessInstanceByKey("studentLevaeProcess7"); // 流程定义表的KEY字段值
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    /**
     * 查看任务
     */
    @Test
    public void findTask() {
        List<Task> taskList = processEngine.getTaskService() // 任务相关Service
                .createTaskQuery() // 创建任务查询
//			.taskAssignee("张三") // 指定某个人
                .processInstanceId("55005")//流程实例
//                .taskUnassigned()//没有审批的任务
                .list();
        for (Task task : taskList) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("任务委派人:" + task.getAssignee());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
        }
    }


    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        processEngine.getTaskService() // 任务相关Service
                .complete("50012");
    }


}
