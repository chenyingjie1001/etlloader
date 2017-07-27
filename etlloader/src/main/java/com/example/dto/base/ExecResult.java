package com.example.dto.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6334881864929748767L;

	private String errorMsg ;
	private String inputMsg ;
	private Integer exitValue ;
}
