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
 * course-demo-1 ѧ���������
 * @author Barrett
 *
 */
public class StudentLeaveProcess {

	/**
	 * ��ȡĬ����������ʵ�������Զ���ȡactiviti.cfg.xml�ļ�
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * �������̶���
	 * # ���̲����
	 * SELECT * FROM `act_re_deployment`;
	 * # ���̶����
	 * SELECT * FROM `act_re_procdef`;
	 * # ��Դ�ļ���
	 * SELECT * FROM `act_ge_bytearray`;
	 */
	@Test
	public void deploy(){
		Deployment deployment=processEngine.getRepositoryService() // ��ȡ�������Service
				.createDeployment() // ��������
				.addClasspathResource("diagrams/StudentLeaveProcess.bpmn") // ������Դ�ļ�
				.addClasspathResource("diagrams/StudentLeaveProcess.png") // ������Դ�ļ�
				.name("ѧ���������") // ��������
				.deploy(); // ����
		System.out.println("���̲���ID:"+deployment.getId()); 
		System.out.println("���̲���Name:"+deployment.getName());
	}
	
	/**
	 * ��������ʵ��
	 * studentLevaeProcess: act_re_procdef �е�key
	 * ����ʵ������ʱ ִ�ж����(����ִ�еĽڵ�)
	 * SELECT * FROM `act_ru_execution`;
	 * # ����ʵ������ʱ �����ϵ��
	 * SELECT * FROM `act_ru_identitylink`;
	 * # ����ʵ������ʱ �û������
	 * SELECT * FROM `act_ru_task`;
	 * # ��ڵ���ʷ��
	 * SELECT * FROM `act_hi_actinst`;
	 * # �����ϵ�� ��ʷ
	 * SELECT * FROM `act_hi_identitylink`;
	 * # ����ʵ���� ��ʷ
	 * SELECT * FROM `act_hi_procinst`;
	 * # ��ʷ�����
	 * SELECT * FROM `act_hi_taskinst`;
	 */
	@Test
	public void start(){
		ProcessInstance pi=processEngine.getRuntimeService() // ����ʱService
			.startProcessInstanceByKey("studentLevaeProcess"); // ���̶�����KEY�ֶ�ֵ
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
			.taskAssignee("����") // ָ��ĳ����
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
			.complete("40002");
	}
	
	/**
	 * ��ѯ����״̬������ִ�� or �Ѿ�ִ�н�����
	 */
	@Test
	public void processState(){
		ProcessInstance pi=processEngine.getRuntimeService() // ��ȡ����ʱService
			.createProcessInstanceQuery() // ��������ʵ����ѯ
			.processInstanceId("35001") // ������ʵ��id��ѯ
			.singleResult();
		if(pi!=null){
			System.out.println("��������ִ�У�");
		}else{
			System.out.println("�����Ѿ�ִ�н�����");
		}
	}
	
	/**
	 * ��ʷ�����ѯ
	 * act_hi_taskinst
	 */
	@Test
	public void historyTaskList(){
		List<HistoricTaskInstance> list=processEngine.getHistoryService() // ��ʷ���Service
			.createHistoricTaskInstanceQuery() // ������ʷ����ʵ����ѯ
			.processInstanceId("35001") // ������ʵ��id��ѯ
			.finished() // ��ѯ�Ѿ���ɵ�����
			.list(); 
		for(HistoricTaskInstance hti:list){
			System.out.println("����ID:"+hti.getId());
			System.out.println("����ʵ��ID:"+hti.getProcessInstanceId());
			System.out.println("�������ƣ�"+hti.getName());
			System.out.println("�����ˣ�"+hti.getAssignee());
			System.out.println("��ʼʱ�䣺"+hti.getStartTime());
			System.out.println("����ʱ�䣺"+hti.getEndTime());
			System.out.println("=================================");
		}
	}
	
	/**
	 * ��ʷ���ѯ
	 * act_hi_actinst
	 */
	@Test
	public void historyActInstanceList(){
		List<HistoricActivityInstance>  list=processEngine.getHistoryService() // ��ʷ���Service
			.createHistoricActivityInstanceQuery() // ������ʷ�ʵ����ѯ
			.processInstanceId("35001") // ִ������ʵ��id
			.finished()
			.list();
		for(HistoricActivityInstance hai:list){
			System.out.println("�ID:"+hai.getId());
			System.out.println("����ʵ��ID:"+hai.getProcessInstanceId());
			System.out.println("����ƣ�"+hai.getActivityName());
			System.out.println("�����ˣ�"+hai.getAssignee());
			System.out.println("��ʼʱ�䣺"+hai.getStartTime());
			System.out.println("����ʱ�䣺"+hai.getEndTime());
			System.out.println("=================================");
		}
	}

}
