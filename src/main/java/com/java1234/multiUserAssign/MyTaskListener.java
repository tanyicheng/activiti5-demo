package com.java1234.multiUserAssign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskListener implements TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		// delegateTask.setAssignee("赵六"); // 指定办理人
		delegateTask.addCandidateUser("张三");
		delegateTask.addCandidateUser("李四");
		delegateTask.addCandidateUser("王五");
	
	}

}
