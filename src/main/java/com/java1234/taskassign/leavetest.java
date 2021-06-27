package com.java1234.taskassign;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * course-14 设置审批人 TaskListener 监听实现；
 *
 * @author created by barrett in 2021/4/2 22:31
 **/
public class leavetest {

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
                .addClasspathResource("diagrams/请假流程.bpmn") // 加载资源文件
//                .name("学生请假流程7") // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
        System.out.println("-------------------");

        start2();
    }

    @Test
    public void start2() {

        //增加监听器
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessLeaveTaskListener", new BusinessleaveTaskListener());
        map.put("businessLeaveEndListener", new BusinessLeaveEndListener());
        map.put("days", "2");
//        map.put("user", "mpc");

        ProcessInstance pi = processEngine.getRuntimeService() // 运行时Service
                .startProcessInstanceByKey("leaveProcess", map); // 流程定义表的KEY字段值
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());

        System.out.println("-------------------");
        //查询任务
        TaskService taskService = processEngine.getTaskService();
        Task task = processEngine.getTaskService() // 任务相关Service
                .createTaskQuery() // 创建任务查询
                .processInstanceId(pi.getId())//流程实例
                .singleResult();

        System.out.println("任务ID:" + task.getId());
        System.out.println("任务名称:" + task.getName());
        System.out.println("任务创建时间:" + task.getCreateTime());
        System.out.println("任务委派人:" + task.getAssignee());
        System.out.println("流程实例ID:" + task.getProcessInstanceId());
        //完成任务
//        taskService.complete(task.getId());
    }

    /**
     * 查看任务
     */
    @Test
    public void findTask() {
        List<Task> taskList = processEngine.getTaskService() // 任务相关Service
                .createTaskQuery() // 创建任务查询
//			.taskAssignee("张三") // 指定某个人
                .processInstanceId("85005")//流程实例
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
                .complete("90002");
        System.out.println("任务审批完成 ------------ \n");

        findTask();
    }


}
