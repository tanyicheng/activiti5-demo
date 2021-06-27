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
 * course-14 ���������� TaskListener ����ʵ�֣�
 *
 * @author created by barrett in 2021/4/2 22:31
 **/
public class leavetest {

    /**
     * ��ȡĬ����������ʵ�������Զ���ȡactiviti.cfg.xml�ļ�
     */
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * �������̶���
     */
    @Test
    public void deploy() {
        Deployment deployment = processEngine.getRepositoryService() // ��ȡ�������Service
                .createDeployment() // ��������
                .addClasspathResource("diagrams/�������.bpmn") // ������Դ�ļ�
//                .name("ѧ���������7") // ��������
                .deploy(); // ����
        System.out.println("���̲���ID:" + deployment.getId());
        System.out.println("���̲���Name:" + deployment.getName());
        System.out.println("-------------------");

        start2();
    }

    @Test
    public void start2() {

        //���Ӽ�����
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessLeaveTaskListener", new BusinessleaveTaskListener());
        map.put("businessLeaveEndListener", new BusinessLeaveEndListener());
        map.put("days", "2");
//        map.put("user", "mpc");

        ProcessInstance pi = processEngine.getRuntimeService() // ����ʱService
                .startProcessInstanceByKey("leaveProcess", map); // ���̶�����KEY�ֶ�ֵ
        System.out.println("����ʵ��ID:" + pi.getId());
        System.out.println("���̶���ID:" + pi.getProcessDefinitionId());

        System.out.println("-------------------");
        //��ѯ����
        TaskService taskService = processEngine.getTaskService();
        Task task = processEngine.getTaskService() // �������Service
                .createTaskQuery() // ���������ѯ
                .processInstanceId(pi.getId())//����ʵ��
                .singleResult();

        System.out.println("����ID:" + task.getId());
        System.out.println("��������:" + task.getName());
        System.out.println("���񴴽�ʱ��:" + task.getCreateTime());
        System.out.println("����ί����:" + task.getAssignee());
        System.out.println("����ʵ��ID:" + task.getProcessInstanceId());
        //�������
//        taskService.complete(task.getId());
    }

    /**
     * �鿴����
     */
    @Test
    public void findTask() {
        List<Task> taskList = processEngine.getTaskService() // �������Service
                .createTaskQuery() // ���������ѯ
//			.taskAssignee("����") // ָ��ĳ����
                .processInstanceId("85005")//����ʵ��
//                .taskUnassigned()//û������������
                .list();
        for (Task task : taskList) {
            System.out.println("����ID:" + task.getId());
            System.out.println("��������:" + task.getName());
            System.out.println("���񴴽�ʱ��:" + task.getCreateTime());
            System.out.println("����ί����:" + task.getAssignee());
            System.out.println("����ʵ��ID:" + task.getProcessInstanceId());
        }
    }


    /**
     * �������
     */
    @Test
    public void completeTask() {
        processEngine.getTaskService() // �������Service
                .complete("90002");
        System.out.println("����������� ------------ \n");

        findTask();
    }


}
