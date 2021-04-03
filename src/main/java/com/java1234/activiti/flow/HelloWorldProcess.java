package com.java1234.activiti.flow;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
/**
 * course-2 ��������ʵ��
 * @author Barrett
 *
 */
public class HelloWorldProcess {

	/**
	 * ��ȡĬ����������ʵ�������Զ���ȡactiviti.cfg.xml�ļ�
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 	1���������̶���
	 */
	@Test
	public void deploy(){
		Deployment deployment=processEngine.getRepositoryService() // ��ȡ�������Service
				.createDeployment() // ��������
				.addClasspathResource("diagrams/HelloWorld.bpmn") // ������Դ�ļ�
				.addClasspathResource("diagrams/HelloWorld.png") // ������Դ�ļ�
				.name("HelloWorld����") // ��������
				.deploy(); // ����
		System.out.println("���̲���ID:"+deployment.getId()); 
		System.out.println("���̲���Name:"+deployment.getName());
	}
	
	/**
	 * 	2����������ʵ��
	 */
	@Test
	public void start(){
		ProcessInstance pi=processEngine.getRuntimeService() // ����ʱService
			.startProcessInstanceByKey("myFirstProcess"); // ���̶�����KEY�ֶ�ֵ
		System.out.println("����ʵ��ID:"+pi.getId());
		System.out.println("���̶���ID:"+pi.getProcessDefinitionId()); 
	}
	
	/**
	 * 	3���鿴����
	 */
	@Test
	public void findTask(){
		List<Task> taskList=processEngine.getTaskService() // �������Service
			.createTaskQuery() // ���������ѯ
			.taskAssignee("java1234_С��") // ָ��ĳ����
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
	 * 	4���������
	 */
	@Test
	public void completeTask(){
		processEngine.getTaskService() // �������Service
			.complete("2504");//�����ID
	}
	
	
}
