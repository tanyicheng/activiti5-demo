package com.java1234.taskassign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * course-14 �����õļ���
 * @author created by barrett in 2021/4/3 09:58
 **/
public class MyTaskListener implements TaskListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		delegateTask.setAssignee("����"); // ָ��������
	}

}
