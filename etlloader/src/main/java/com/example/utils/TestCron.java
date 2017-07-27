package com.example.utils;

import java.text.ParseException;
import org.quartz.impl.triggers.CronTriggerImpl;

public class TestCron {
	public static boolean test(String frequencyCron){
		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
		try {
			cronTriggerImpl.setCronExpression(frequencyCron);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
