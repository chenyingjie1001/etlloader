package com.example.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;

/**
 * bean和Map的互相转换
 * 用到apache commons 的beanutils包
 * @author yingjie.chen
 */
public class BeanUtil {

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
  
        Object obj = beanClass.newInstance();  
  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
  
        return obj;  
    }    
      
    public static Map<String, Object> objectToMap(Object obj) {  
        if(obj == null)  
            return null;   
  
        BeanMap map = new org.apache.commons.beanutils.BeanMap(obj);
        Map<String, Object> hashMap = new HashMap<String, Object>();
        for(Object key : map.keySet()){
        	hashMap.put(key+"", map.get(key));
        }
        return hashMap;
    } 
    
    public static Set<String> setOperation(Set<String> newSet,Set<String> oldSet){
		Set<String> result=new HashSet<>();
    	result.clear();
		result.addAll(newSet);
		result.removeAll(oldSet);
		return result;
    }
    
    public static Set<String> setIntersection(Set<String> newSet,Set<String> oldSet){
		Set<String> result=new HashSet<>();
		result.clear();
		result.addAll(newSet);
		result.retainAll(oldSet);
		return result;
    }
}
