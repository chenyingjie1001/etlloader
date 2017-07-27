package com.example.dto.base;

import java.io.Serializable;

import lombok.Data;


@Data
public class Pager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1920526534892840462L;
	private int pageNum = 1;
	private int pageSize = 10;
}
