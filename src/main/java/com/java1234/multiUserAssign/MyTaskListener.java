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
		// delegateTask.setAssignee("����"); // ָ��������
		delegateTask.addCandidateUser("����");
		delegateTask.addCandidateUser("����");
		delegateTask.addCandidateUser("����");
	
	}

}
