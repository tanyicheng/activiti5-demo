package com.java1234.activiti.table;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * course-1 ����
 * @author Barrett
 *
 */
public class ActivitiTest01 {

	/**
	 * ����Activiti��Ҫ��25��
	 */
	@Test
	public void testCreateTable(){
		ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration(); // ��ȡ������������
		pec.setJdbcDriver("com.mysql.jdbc.Driver"); // ��������
		pec.setJdbcUrl("jdbc:mysql://192.168.91.100:3306/db_activiti"); // �������ӵ�ַ
		pec.setJdbcUsername("root"); // �û���
		pec.setJdbcPassword("123456"); // ����
		
		/**
		 * ����ģʽ  true �Զ������͸��±�
		 */
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		// ��ȡ�����������
		ProcessEngine pe=pec.buildProcessEngine(); 
	}
	
	/**
	 * 	����Activiti��Ҫ��25�� ʹ�������ļ�
	 * http://blog.java1234.com/blog/articles/76.html
	 */
	@Test
	public void testCreateTableWithXml(){
		 // ��������
	    ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
	    // ��ȡ�����������
	    ProcessEngine processEngine=pec.buildProcessEngine();
	}
}
