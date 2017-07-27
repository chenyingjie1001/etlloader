package com.example.dto.task;

import java.io.Serializable;
import java.util.Date;

import com.example.dto.base.DateSection;
import com.example.dto.base.Pager;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class Task extends DateSection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5853681278106600126L;

	private Integer id; //
	private Integer datasourceid; //对应的数据源id
	private Integer targetsourceid; //目标数据库
	private Integer parentid; //父任务id
	private String checktables; //选择的表已,区分
	private String checkcolumns; //选择的列,区分
	private String sourcetable; //源表
	private String targettable; //目标表
	private String taskstatus; // 0弃用 or 1生效 default=1
	private String isall; //0 or 1 全量1 default 1
	private String frequency; //执行频率
	private String tasktype; //任务类型
	private Integer retry; //重试次数
	private Integer retrywaittime; //重试等待时间
	private Integer outofretrytime; //超时
	private String des; //描述
	private String sql; //sql模式  若有sql，就按sql模式
	private String wheresql; //表达式
	private Date createdate; //创建时间
	private String createuser; //创建人
	private Pager pager = new Pager();;
}
