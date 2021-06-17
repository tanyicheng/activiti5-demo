package com.java1234.activiti.flow;


import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * course-demo-1 ѧ���������
 *
 * @author Barrett
 */
public class StudentLeaveProcess {

    /**
     * ��ȡĬ����������ʵ�������Զ���ȡactiviti.cfg.xml�ļ�
     */
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    @Test
    public void createUser() {
        IdentityService identityService = processEngine.getIdentityService();

        //��Ŀ��ÿ����һ�����û�����Ӧ��Ҫ����һ��Activiti�û�
        //���ߵ�userId��userNameһ��
        User admin = identityService.newUser("1");
        admin.setLastName("admin");
        identityService.saveUser(admin);

        //��Ŀ��ÿ����һ����ɫ����Ӧ��Ҫ����һ��Activiti�û���
        Group adminGroup = identityService.newGroup("1");
        adminGroup.setName("g����Ա");
        identityService.saveGroup(adminGroup);

        //�û����û����ϵ��
        identityService.createMembership("1", "1");
    }

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
    public void deploy() {
        Deployment deployment = processEngine.getRepositoryService() // ��ȡ�������Service
                .createDeployment() // ��������
                .addClasspathResource("diagrams/StudentLeaveProcess.bpmn") // ������Դ�ļ�
                .addClasspathResource("diagrams/StudentLeaveProcess.png") // ������Դ�ļ�
                .name("ѧ���������") // ��������
                .deploy(); // ����
        System.out.println("���̲���ID:" + deployment.getId());
        System.out.println("���̲���Name:" + deployment.getName());
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
    public void start() {
        ProcessInstance pi = processEngine.getRuntimeService() // ����ʱService
                .startProcessInstanceByKey("studentLevaeProcess"); // ���̶�����KEY�ֶ�ֵ
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
//                .taskAssignee("�೤") // ָ��ĳ����
                .list();
        for (Task task : taskList) {
            System.out.println("����ID:" + task.getId());
            System.out.println("��������:" + task.getName());
            System.out.println("���񴴽�ʱ��:" + task.getCreateTime());
            System.out.println("����������:" + task.getAssignee());
            System.out.println("����ʵ��ID:" + task.getProcessInstanceId());
            System.out.println("-----------------------------");
        }
    }


    /**
     * �������
     */
    @Test
    public void completeTask() {
        String taskId = "27504";
        // �������Service
        TaskService taskService = processEngine.getTaskService();
        taskService.setVariableLocal(taskId, "reason", "�н���");//������������̱���
        taskService.setVariableLocal(taskId, "status", "YES");
        taskService.complete(taskId);//
    }

    /**
     * ��ѯ����״̬������ִ�� or �Ѿ�ִ�н�����
     */
    @Test
    public void processState() {
        ProcessInstance pi = processEngine.getRuntimeService() // ��ȡ����ʱService
                .createProcessInstanceQuery() // ��������ʵ����ѯ
                .processInstanceId("10001") // ������ʵ��id��ѯ
                .singleResult();
        if (pi != null) {
            System.out.println("��������ִ�У�");
        } else {
            System.out.println("�����Ѿ�ִ�н�����");
        }
    }

    /**
     * ��ѯ����ı���������ǩʱ�������
     * @author created by barrett in 2021/6/16 23:06
     **/
    @Test
    public void getTaskVariable() {
        TaskService taskService = processEngine.getTaskService();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricVariableInstance reason1 = historyService.createHistoricVariableInstanceQuery().variableName("reason").taskId("27504").singleResult();

        //����ɵĲ����ڵ�ǰ�����в�ѯ
//        String reason = (String) taskService.getVariableLocal("27504", "reason");
    }

    /**
     * ��ʷ�����ѯ
     * act_hi_taskinst
     */
    @Test
    public void historyTaskList() {
        List<HistoricTaskInstance> list = processEngine.getHistoryService() // ��ʷ���Service
                .createHistoricTaskInstanceQuery() // ������ʷ����ʵ����ѯ
//                .processInstanceId("10001") // ������ʵ��id��ѯ
//                .taskId("27504")
//                .finished() // ��ѯ�Ѿ���ɵ�����
//                .unfinished()
                .processDefinitionId("studentLevaeProcess:2:7504")
                .list();
        for (HistoricTaskInstance hti : list) {
            System.out.println("����ID:" + hti.getId());
            System.out.println("����ʵ��ID:" + hti.getProcessInstanceId());
            System.out.println("���̶���ID:" + hti.getProcessDefinitionId());
            System.out.println("�������ƣ�" + hti.getName());
            System.out.println("�����ˣ�" + hti.getAssignee());
            System.out.println("��ʼʱ�䣺" + hti.getStartTime());
            System.out.println("����ʱ�䣺" + hti.getEndTime());
            Map<String, Object> processVariables = hti.getProcessVariables();
            Map<String, Object> taskLocalVariables = hti.getTaskLocalVariables();
            String reason = (String) taskLocalVariables.get("reason");
            if (reason != null)
                System.out.println("���ɣ�" + reason+"<<<<<<<<<<<<<<<<<<<<");

            System.out.println("------------------------");
        }
    }

    /**
     * ��ʷ���ѯ
     * act_hi_actinst
     */
    @Test
    public void historyActInstanceList() {
        List<HistoricActivityInstance> list = processEngine.getHistoryService() // ��ʷ���Service
                .createHistoricActivityInstanceQuery() // ������ʷ�ʵ����ѯ
                .processInstanceId("10001") // ִ������ʵ��id
                .finished()
                .list();
        for (HistoricActivityInstance hai : list) {
            System.out.println("�ID:" + hai.getId());
            System.out.println("����ʵ��ID:" + hai.getProcessInstanceId());
            System.out.println("����ƣ�" + hai.getActivityName());
            System.out.println("�����ˣ�" + hai.getAssignee());
            System.out.println("��ʼʱ�䣺" + hai.getStartTime());
            System.out.println("����ʱ�䣺" + hai.getEndTime());
            System.out.println("=================================");
        }
    }


    /**
     * ����ʵ��
     *
     * @author created by barrett in 2021/6/13 18:53
     **/
    @Test
    public void historyTest() {
//		HistoricProcessInstanceQueryRequest
        HistoricProcessInstanceQuery query = processEngine.getHistoryService().createHistoricProcessInstanceQuery();
//		query.processDefinitionId("");
//		query.

        List<HistoricProcessInstance> list = query.list();
        for (HistoricProcessInstance hpi : list) {
            System.out.println(hpi.getId());
            System.out.println(hpi.getProcessDefinitionId());
            System.out.println(hpi.getName());
            System.out.println(hpi.getStartTime());
            System.out.println(hpi.getEndTime());
            System.out.println(hpi.getBusinessKey());
            System.out.println("---------------------------");
        }

    }


}
