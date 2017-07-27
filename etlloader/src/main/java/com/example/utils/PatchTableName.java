package com.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yunlong.zhang
 *
 */
public class PatchTableName {
	
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
	
	public static String PatchTName(String tName, String targettableNamePostfix){
		String targettableName = tName + targettableNamePostfix;
		try {
			int tNameLong = tName.length();
			int targettableNameLong = targettableName.length();
			if(targettableNameLong>30){
				tName = tName.substring(0, tNameLong-(targettableNameLong-30));
				targettableName = tName + targettableNamePostfix;
			}
		} catch (Exception e) {
			logger.error("PatchTName 出错");
			logger.error("libzone.cn exception:", e);
			e.printStackTrace();
		}
		return targettableName;
	}
	
}
