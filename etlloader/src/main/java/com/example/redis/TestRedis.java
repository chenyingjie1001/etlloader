package com.example.redis;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.redisson.Config;
import org.redisson.Redisson;

import com.example.utils.Constants;

import redis.clients.jedis.Jedis;

/**
 * @author yingjie.chen
 */
public class TestRedis {

	/*public static void main(String[] args) {
		 // 1.初始化  
        Config config = new Config();  
        config.setConnectionPoolSize(10);  
        config.addAddress("172.16.3.73:6379");  
        Redisson redisson = Redisson.create(config);  
        System.out.println("reids连接成功...");  
  
        // 2.测试concurrentMap,put方法的时候就会同步到redis中  
        ConcurrentMap<String, Object> map = redisson.getMap("FirstMap");  
        map.put("wuguowei", "男");  
        map.put("zhangsan", "nan");  
        map.put("lisi", "女");  
  
        ConcurrentMap resultMap = redisson.getMap("FirstMap");  
        System.out.println("resultMap==" + resultMap.keySet());  
  
        // 2.测试Set集合  
        Set mySet = redisson.getSet("MySet");  
        mySet.add("wuguowei");  
        mySet.add("lisi");  
  
        Set resultSet = redisson.getSet("MySet");  
        System.out.println("resultSet===" + resultSet.size());  
          
        //3.测试Queue队列  
        Queue myQueue = redisson.getQueue("FirstQueue");  
        myQueue.add("wuguowei");  
        myQueue.add("lili");  
        myQueue.add("zhangsan");  
        myQueue.peek();  
        myQueue.poll();  
  
        Queue resultQueue=redisson.getQueue("FirstQueue");  
        System.out.println("resultQueue==="+resultQueue);  
          
        // 关闭连接  
        redisson.shutdown(); 
	}*/

	public static void main(String[] args) {
		Jedis jedis = new Jedis("172.16.3.73", 6379);
		Set<String> lists = jedis.keys("*6144eba9-11f5-4afc-a436-19bef2555228");
		
		System.out.println(lists);
	}
}
