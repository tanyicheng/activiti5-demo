package com.java1234.taskassign;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.io.Serializable;

/**
 * 请假审批监听器
 * @author created by barrett in 2021/6/26 21:02
 **/
public class BusinessLeaveEndListener implements ExecutionListener, Serializable {


    /**
     * 这里监听是在配置节点前调用，并不是配置监听节点
     * @author created by barrett in 2021/6/27 12:31
     **/
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("end  被监听啦");

    }
}
