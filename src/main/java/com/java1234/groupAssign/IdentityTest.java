package com.java1234.groupAssign;


import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.Test;
/**
 * course-18 增加 用户-组 测试
 * @author created by barrett in 2021/4/3 09:58
 **/
public class IdentityTest {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 添加用户测试
	 */
	@Test
	public void testSaveUser(){
		IdentityService indentityService=processEngine.getIdentityService();
		User user=new UserEntity(); // 实例化用户实体
		user.setId("lisi");
		user.setPassword("123");
		user.setEmail("1234@qq.com");
		indentityService.saveUser(user);

		user.setId("wangwu");
		indentityService.saveUser(user);

	}

	/**
	 * 删除用户
	 */
	@Test
	public void testDeleteUser(){
		IdentityService indentityService=processEngine.getIdentityService();
		indentityService.deleteUser("lisi");
	}
	
	/**
	 * 添加组
	 */
	@Test
	public void testSaveGroup(){
		IdentityService indentityService=processEngine.getIdentityService();
		Group group=new GroupEntity();
		group.setId("dev");
		indentityService.saveGroup(group);
		group.setId("test");
		indentityService.saveGroup(group);
	}
	
	/**
	 * 测试删除组
	 */
	@Test
	public void testDeleteGroup(){
		IdentityService indentityService=processEngine.getIdentityService();
		indentityService.deleteGroup("test");
	}
	
	/**
	 * 测试添加用户和组的关联关系
	 */
	@Test
	public void testSaveMembership(){
		IdentityService indentityService=processEngine.getIdentityService();
		indentityService.createMembership("wangwu", "test");
		indentityService.createMembership("lisi", "test");
	}
	
	/**
	 * 测试删除用户和组的关联关系
	 */
	@Test
	public void testDeleteMembership(){
		IdentityService indentityService=processEngine.getIdentityService();
		indentityService.deleteMembership("lisi", "dev");
	}
}
