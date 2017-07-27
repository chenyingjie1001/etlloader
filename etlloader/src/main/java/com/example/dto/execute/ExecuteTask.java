package com.example.dto.execute;

import java.io.Serializable;
import java.util.Date;

import com.example.dto.base.DateSection;
import com.example.dto.base.Pager;
import com.example.dto.task.Task;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExecuteTask extends DateSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6391616218950744759L;

	private Integer id;
	private Integer taskid;//对应任务id
	private Date createdate;//创建时间
	private Date exedate;//执行时间
	private Date completedate;//完成时间
	private Integer exestatus;// -1初始化 0就绪，1进行中，2已完成，3失败 4超时
	private Integer retrytime; //重跑次数 默认0
	private String exelog;//执行日志
	private String isall;
	private Task task;
	private Pager pager = new Pager();
}
