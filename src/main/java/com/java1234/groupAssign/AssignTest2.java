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
 * course-20 ʹ�����̱������� Candidate groups
 * fixme-1 �������ʱ���������ˣ�
 * @author created by barrett in 2021/4/3 10:12
 **/
public class AssignTest2 {

	/**
	 * ��ȡĬ����������ʵ�������Զ���ȡactiviti.cfg.xml�ļ�
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * �������̶���
	 */
	@Test
	public void deploy(){
		Deployment deployment=processEngine.getRepositoryService() // ��ȡ�������Service
				.createDeployment() // ��������
				.addClasspathResource("diagrams/GroupProcess2.bpmn") // ������Դ�ļ�
				.addClasspathResource("diagrams/GroupProcess2.png") // ������Դ�ļ�
				.name("ѧ���������5") // ��������
				.deploy(); // ����
		System.out.println("���̲���ID:"+deployment.getId()); 
		System.out.println("���̲���Name:"+deployment.getName());
	}
	
	/**
	 * ��������ʵ��
	 */
	@Test
	public void start(){
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("groupId", "test");
		ProcessInstance pi=processEngine.getRuntimeService() // ����ʱService
			.startProcessInstanceByKey("groupProcess02",variables); // ���̶�����KEY�ֶ�ֵ
		System.out.println("����ʵ��ID:"+pi.getId());
		System.out.println("���̶���ID:"+pi.getProcessDefinitionId()); 
	}
	
	
	/**
	 * �鿴����
	 */
	@Test
	public void findTask(){
		List<Task> taskList=processEngine.getTaskService() // �������Service
			.createTaskQuery() // ���������ѯ
			//.taskAssignee("����") // ָ��ĳ����
			.taskCandidateUser("lisi") // ָ����ѡ��
			.list();
		for(Task task:taskList){
			System.out.println("����ID:"+task.getId()); 
			System.out.println("��������:"+task.getName());
			System.out.println("���񴴽�ʱ��:"+task.getCreateTime());
			System.out.println("����ί����:"+task.getAssignee());
			System.out.println("����ʵ��ID:"+task.getProcessInstanceId());
		}
	}
	
	
	/**
	 * �������
	 */
	@Test
	public void completeTask(){
		processEngine.getTaskService() // �������Service
			.complete("257505");
	}
	

}
