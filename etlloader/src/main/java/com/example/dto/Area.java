package com.example.dto;

import java.io.Serializable;

import lombok.Data;


@Data
public class Area implements Serializable{

	/**
	 * serialVersionUID
	 * auto add
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int pid;
	private int lv;
	
}
