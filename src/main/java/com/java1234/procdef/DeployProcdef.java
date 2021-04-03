package com.java1234.procdef;


import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * course-3	部署流程定义两种方式
 * @author Barrett
 *
 */
public class DeployProcdef {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deployWithClassPath(){
		Deployment deployment=processEngine.getRepositoryService() // 获取部署相关Service
				.createDeployment() // 创建部署
				.addClasspathResource("diagrams/HelloWorld2.bpmn") // 加载资源文件
				.addClasspathResource("diagrams/HelloWorld2.png") // 加载资源文件
				.name("HelloWorld流程") // 流程名称
				.deploy(); // 部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}

	/**
	 * 部署流程定义使用zip方式
	 */
	@Test
	public void deployWithZip(){
		InputStream inputStream=this.getClass() // 取得当前class对象
			.getClassLoader() // 获取类加载器
			.getResourceAsStream("diagrams/helloWorld.zip"); // 获取指定文件资源流
		
		ZipInputStream zipInputStream=new ZipInputStream(inputStream); // 实例化zip输入流
		Deployment deployment=processEngine.getRepositoryService() // 获取部署相关Service
				.createDeployment() // 创建部署
				.addZipInputStream(zipInputStream) // 添加zip输入流
				.name("HelloWorld流程") // 流程名称
				.deploy(); // 部署
		System.out.println("流程部署ID:"+deployment.getId()); 
		System.out.println("流程部署Name:"+deployment.getName());
	}
}
