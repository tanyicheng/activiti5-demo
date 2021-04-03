package com.java1234.activiti.flow;


import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * course-demo-1 学生请假流程
 * @author Barrett
 *
 */
public class StudentLeaveProcess {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 * # 流程部署表
	 * SELECT * FROM `act_re_deployment`;
	 * # 流程定义表
	 * SELECT * FROM `act_re_procdef`;
	 * # 资源文件表
	 * SELECT * FROM `act_ge_bytearray`;
	 */
	@Test
	public void deploy(){
		Deployment deployment=processEngine.getRepositoryService() // 获取部署相关Service
				.createDeployment() // 创建部署
				.addClasspathResource("diagrams/StudentLeaveProcess.bpmn") // 加载资源文件
				.addClasspathResource("diagrams/StudentLeaveProcess.png") // 加载资源文件
				.name("学生请假流程") // 流程名称
				.deploy(); // 部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}
	
	/**
	 * 启动流程实例
	 * studentLevaeProcess: act_re_procdef 中的key
	 * 流程实例运行时 执行对象表(正在执行的节点)
	 * SELECT * FROM `act_ru_execution`;
	 * # 流程实例运行时 身份联系表
	 * SELECT * FROM `act_ru_identitylink`;
	 * # 流程实例运行时 用户任务表
	 * SELECT * FROM `act_ru_task`;
	 * # 活动节点历史表
	 * SELECT * FROM `act_hi_actinst`;
	 * # 身份联系表 历史
	 * SELECT * FROM `act_hi_identitylink`;
	 * # 流程实例表 历史
	 * SELECT * FROM `act_hi_procinst`;
	 * # 历史任务表
	 * SELECT * FROM `act_hi_taskinst`;
	 */
	@Test
	public void start(){
		ProcessInstance pi=processEngine.getRuntimeService() // 运行时Service
			.startProcessInstanceByKey("studentLevaeProcess"); // 流程定义表的KEY字段值
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
			.taskAssignee("王五") // 指定某个人
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
			.complete("40002");
	}
	
	/**
	 * 查询流程状态（正在执行 or 已经执行结束）
	 */
	@Test
	public void processState(){
		ProcessInstance pi=processEngine.getRuntimeService() // 获取运行时Service
			.createProcessInstanceQuery() // 创建流程实例查询
			.processInstanceId("35001") // 用流程实例id查询
			.singleResult();
		if(pi!=null){
			System.out.println("流程正在执行！");
		}else{
			System.out.println("流程已经执行结束！");
		}
	}
	
	/**
	 * 历史任务查询
	 * act_hi_taskinst
	 */
	@Test
	public void historyTaskList(){
		List<HistoricTaskInstance> list=processEngine.getHistoryService() // 历史相关Service
			.createHistoricTaskInstanceQuery() // 创建历史任务实例查询
			.processInstanceId("35001") // 用流程实例id查询
			.finished() // 查询已经完成的任务
			.list(); 
		for(HistoricTaskInstance hti:list){
			System.out.println("任务ID:"+hti.getId());
			System.out.println("流程实例ID:"+hti.getProcessInstanceId());
			System.out.println("任务名称："+hti.getName());
			System.out.println("办理人："+hti.getAssignee());
			System.out.println("开始时间："+hti.getStartTime());
			System.out.println("结束时间："+hti.getEndTime());
			System.out.println("=================================");
		}
	}
	
	/**
	 * 历史活动查询
	 * act_hi_actinst
	 */
	@Test
	public void historyActInstanceList(){
		List<HistoricActivityInstance>  list=processEngine.getHistoryService() // 历史相关Service
			.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
			.processInstanceId("35001") // 执行流程实例id
			.finished()
			.list();
		for(HistoricActivityInstance hai:list){
			System.out.println("活动ID:"+hai.getId());
			System.out.println("流程实例ID:"+hai.getProcessInstanceId());
			System.out.println("活动名称："+hai.getActivityName());
			System.out.println("办理人："+hai.getAssignee());
			System.out.println("开始时间："+hai.getStartTime());
			System.out.println("结束时间："+hai.getEndTime());
			System.out.println("=================================");
		}
	}

}
