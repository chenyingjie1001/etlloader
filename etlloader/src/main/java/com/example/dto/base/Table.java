package com.example.dto.base;

import java.io.Serializable;

import lombok.Data;

/**
 * 配置前端框架
 * @author yingjie.chen
 *
 */
@Data
public class Table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4509363965364628736L;

	private String title;
	
	private boolean exist;
}
