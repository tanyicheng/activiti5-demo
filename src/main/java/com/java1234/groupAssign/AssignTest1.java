package com.java1234.groupAssign;


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
 * course-19 根据组派发给审批人
 * todo 先要添加用户和组 调用 IdentityTest
 * @author created by barrett in 2021/4/3 09:58
 **/
public class AssignTest1 {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy(){
		Deployment deployment=processEngine.getRepositoryService() // 获取部署相关Service
				.createDeployment() // 创建部署
				.addClasspathResource("diagrams/GroupProcess.bpmn") // 加载资源文件
				.addClasspathResource("diagrams/GroupProcess.png") // 加载资源文件
				.name("学生请假流程5") // 流程名称
				.deploy(); // 部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void start(){
		ProcessInstance pi=processEngine.getRuntimeService() // 运行时Service
			.startProcessInstanceByKey("groupProcess"); // 流程定义表的KEY字段值
		System.out.println("流程实例ID:"+pi.getId());
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId()); 
	}
	
	
	/**
	 * 查看任务
	 */
	@Test
	public void findTask(){
		List<Task> taskList=processEngine.getTaskService() // 任务相关Service
			.createTaskQuery() // 创建任务查询
			//.taskAssignee("李四") // 指定某个人
				.processInstanceId("55001")
//			.taskCandidateUser("zhangsan") // 指定候选人
			.list();
		for(Task task:taskList){
			System.out.println("任务ID:"+task.getId()); 
			System.out.println("任务名称:"+task.getName());
			System.out.println("任务创建时间:"+task.getCreateTime());
			System.out.println("任务委派人:"+task.getAssignee());
			System.out.println("流程实例ID:"+task.getProcessInstanceId());
		}
	}
	
	
	/**
	 * 完成任务
	 */
	@Test
	public void completeTask(){
		processEngine.getTaskService() // 任务相关Service
			.complete("50004");
	}
	

}
