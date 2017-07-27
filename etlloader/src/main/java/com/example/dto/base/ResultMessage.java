package com.example.dto.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import lombok.Data;
@Data
public class ResultMessage<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 579983520800885005L;

	private List<T> data;
	private int httpCode = 200;
	private String msg = "Success";
	private List<Map<String, Object>> mapdata;
	private T t;
	private Map<String, Object> m;
	private PageInfo<T> pageinfo;
	private Date timestamp;
}
