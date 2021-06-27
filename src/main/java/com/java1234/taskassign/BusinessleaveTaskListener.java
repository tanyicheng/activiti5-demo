package com.java1234.taskassign;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class BusinessleaveTaskListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        System.out.println("${businessleaveTaskListener}  ±»¼àÌıÀ²");
    }
}
