package com.example.dto.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class MyExceptionResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1193724980036307572L;
	private int httpCode;
	private String msg;
	private Date timestamp;
}