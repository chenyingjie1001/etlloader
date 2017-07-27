package com.example.dto.previews;

import java.io.Serializable;

import com.example.dto.base.Pager;

import lombok.Data;

@Data
public class Relationship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4453985866687289906L;

	
	private String sourcename;
	private String tablename;
	private Integer taskid;
	private Integer parentid;
	private Pager pager = new Pager();
}
