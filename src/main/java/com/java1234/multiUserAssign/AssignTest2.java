package com.java1234.multiUserAssign;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * course-16 方式二 多用户任务分配（多个审批人只要其中一个人审批即可） 流程变量
 *
 * @author created by barrett in 2021/4/3 09:58
 **/
public class AssignTest2 {

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
                .addClasspathResource("diagrams/multiUserProcess2.bpmn") // 加载资源文件
                .addClasspathResource("diagrams/multiUserProcess2.png") // 加载资源文件
                .name("学生请假流程5") // 流程名称
                .deploy(); // 部署
        System.out.println("流程部署ID:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void start() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userIds", "张三,李四,王五");
        ProcessInstance pi = processEngine.getRuntimeService() // 运行时Service
                .startProcessInstanceByKey("multiUserProcess02", variables); // 流程定义表的KEY字段值
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
                .processInstanceId("40001")//根据流程实例ID查看
//                .taskCandidateUser("李四") // 指定候选人
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
     * 完成任务 TODO 需要加事务
     * 多人审批同一个任务时，需要先设置这个任务的审批人，后调用完成接口
     */
    @Test
    public void completeTask() {
        String taskId= "32505";
        processEngine.getTaskService() // 任务相关Service
                .setAssignee(taskId,"王五");
        processEngine.getTaskService().complete(taskId);

    }



}
