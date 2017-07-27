package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 替换变量中的时间
 * @author yunlong.zhang
 *
 */
public class ReplaceUtils {

	public static String replaceDate(Map<String, Object> m,String writerJson) throws Exception{
		//${OCCUR_DATE}		${OCCUR_DATE,-11}		${OCCUR_DATE,11}
		// 现在创建 matcher 对象
		Matcher mc = Pattern.compile("(OCCUR_DATE(,)?(-)?(\\d)*)").matcher(writerJson);
		Calendar calendar = Calendar.getInstance(); //得到日历
		Date CREATEDATE=(Date)m.get("CREATEDATE");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		while(mc.find()){
			calendar.setTime(CREATEDATE);//把 CREATEDATE 时间赋给日历
			String mc_group=mc.group();
			String[] OCCUR_DATE = mc_group.split(",");
			if(OCCUR_DATE.length==1){
				calendar.add(Calendar.DATE, 0);  //设置为当天(以 CREATEDATE 时间为准)
				writerJson=writerJson.replaceAll("\\$\\{"+mc_group+"\\}"
						, "'"+simpleDateFormat.format(calendar.getTime())+"'");
			}else if(OCCUR_DATE.length==2){
				calendar.add(Calendar.DATE, Integer.parseInt(OCCUR_DATE[1]));  //设置为?天(以 CREATEDATE 时间为准)
				writerJson=writerJson.replaceAll("\\$\\{"+mc_group+"\\}"
						, "'"+simpleDateFormat.format(calendar.getTime())+"'");
			}else{
				throw new Exception("--------SQL变量  时间表达式  ${OCCUR_DATE,?} 异常----------");
			}
		}
		return writerJson;
	}
}
