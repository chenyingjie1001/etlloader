package com.example.utils;

public enum DataBase {

	MYSQL("com.mysql.jdbc.Driver"), 
	ORACLE("oracle.jdbc.driver.OracleDriver"), 
	POSTGRES("org.postgresql.Driver"), 
	SQLSERVER(""), 
	DB2("");
	
	private String classname;
	private DataBase(String classname){
		this.classname = classname;
	}
	
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
}