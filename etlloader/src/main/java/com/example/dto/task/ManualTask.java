package com.example.dto.task;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * 点击手动执行生成任务时候使用
 * @author yingjie.chen
 *
 */

@Data
public class ManualTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 982361464980309975L;
	private int id;
	private String isall; //0 or 1 1 is quanliang
	private Date startdate;
	private Date enddate;
}
