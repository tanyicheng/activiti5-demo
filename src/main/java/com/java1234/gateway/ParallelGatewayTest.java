package com.java1234.gateway;


import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * course-11 ��������
 * 2���˱���������ͨ������������ת
 * @author created by barrett in 2021/4/2 22:31
 **/
public class ParallelGatewayTest {

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
                .addClasspathResource("diagrams/StudentLeaveProcess4.bpmn") // ������Դ�ļ�
                .addClasspathResource("diagrams/StudentLeaveProcess4.png") // ������Դ�ļ�
                .name("ѧ���������4") // ��������
                .deploy(); // ����
        System.out.println("���̲���ID:" + deployment.getId());
        System.out.println("���̲���Name:" + deployment.getName());
    }

    /**
     * ��������ʵ��
     */
    @Test
    public void start() {
        ProcessInstance pi = processEngine.getRuntimeService() // ����ʱService
                .startProcessInstanceByKey("studentLevaeProcess4"); // ���̶�����KEY�ֶ�ֵ
        System.out.println("����ʵ��ID:" + pi.getId());
        System.out.println("���̶���ID:" + pi.getProcessDefinitionId());
    }


    /**
     * �鿴����
     */
    @Test
    public void findTask() {
        List<Task> taskList = processEngine.getTaskService() // �������Service
                .createTaskQuery() // ���������ѯ
                .processInstanceId("12501")//����ʵ��id
//                .taskAssignee("����") // ָ��ĳ����
                .list();
        for (Task task : taskList) {
            System.out.println("����ID:" + task.getId());
            System.out.println("��������:" + task.getName());
            System.out.println("���񴴽�ʱ��:" + task.getCreateTime());
            System.out.println("����ί����:" + task.getAssignee());
            System.out.println("����ʵ��ID:" + task.getProcessInstanceId());

            System.out.println("==========================");
        }
    }


    /**
     * �������
     */
    @Test
    public void completeTask() {
        processEngine.getTaskService() // �������Service
                .complete("20003");
    }


}
