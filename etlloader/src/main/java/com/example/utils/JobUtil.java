package com.example.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 动态生成job.json
 * @author yingjie.chen
 */
public class JobUtil {

	public static String createJson(String dbType, String isReaderOrWriter, Object sql) {
		String dataxtype = "";
		
		if(sql != null && !"".equals(sql)){
			dataxtype = "sql";
		}
		else{
			dataxtype = "table";
		}
		String job = "";
		switch (isReaderOrWriter) {
		case "reader":
			job = "job/reader-"+dataxtype+"-"+dbType.toLowerCase()+".json";
			break;
		case "writer":
			job = "job/writer-"+dbType.toLowerCase()+".json";
			break;
		default:
			job = "job/job.json";
			break;
		}
		return readFile(job);
	}

	public static String getSystem(){
		String wrap = "";
		String system = System.getProperty("os.name");
		if (system.contains("Windows")) {
			wrap = "\r\n";
		} else if (system.contains("Linux")) {
			wrap = "\n";
		} else {
			wrap = "\r";
		}
		return wrap;
	}
	public static String readFile(String job) {
		String result = "";
		String wrap = getSystem();
		InputStream in = JobUtil.class.getClassLoader().getResourceAsStream(job);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String read = null;
			while ((read = br.readLine()) != null) {
				result += read + wrap;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String writeFile(String msg, Object taskid, Object id) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		File fileDir = new File(PropertiesUtil.properties.getProperty("dir.json")+"/"+taskid);
		File file = new File(PropertiesUtil.properties.getProperty("dir.json")+"/"+taskid, taskid + "_" + id + ".json");
		try {
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(msg);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file.getName();
	}

	public static void main(String[] args) {
	}
}
