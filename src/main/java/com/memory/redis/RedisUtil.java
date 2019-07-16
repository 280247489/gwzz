package com.memory.redis;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.*;

/**
 * Created by liuty on 16/8/31.
 */
public class RedisUtil {
    private final static  int dialStatsDB=0;
    private static JedisPool pool=null;
    private final static String dialStatsPrefix="dial.";
 //   private static org.apache.log4j.Logger log = Logger.getLogger(RedisUtil.class.getName());
    public static RedisUtil instance;

    public static void initDialStatsPool(String ip, int port, String pwd,int timeOut,int dialStatsDB){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        jedisPoolConfig.setMaxIdle(150);
        jedisPoolConfig.setMinIdle(10);

        //是否开启jmx监控，默认true
       // jedisPoolConfig.setJmxEnabled(false);
        //资源耗尽后是否等待，默认true。false 并发每次资源不够都会创建新的
       // jedisPoolConfig.setBlockWhenExhausted(false);


        //如果赋值为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽)。
        jedisPoolConfig.setMaxTotal(150);

        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        jedisPoolConfig.setMaxWaitMillis(1000);

        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
       // jedisPoolConfig.setTestOnBorrow(true);

        pool = new JedisPool(jedisPoolConfig,ip,port,timeOut,pwd,dialStatsDB);
    }

    public  static JedisPool getDialStatsPool() {
        return pool;
    }



    public static   Map<String,String>  getByKey(String courseId,JedisPool jedis){
//        String keyHash =CacheConstantConfig.COURSERXT + ":hash:" +courseId;
//        String keySum =CacheConstantConfig.COURSERXT + ":sum:"+courseId;
//        RedisAPI redisHandler = new RedisAPI(RedisUtil.getDialStatsPool());
//        redisHandler.incrBy(keySum,1);
//
//        return  redisHandler.hgetAll(keyHash);
        return  null;

    }


    /**
     *返还jedis资源
     * @param jedis
     */
    public  static void close(final Jedis jedis) {
        if (jedis != null && pool !=null) {
            pool.returnResource(jedis);
        }
    }

    /**
     *释放jedis对象
     * @param jedis
     */
    public static void returnBrokenResource(final Jedis jedis){
        if (jedis != null && pool !=null) {
            pool.returnBrokenResource(jedis);
        }
    }







    /**
     * redis查询
     * @param tel
     * @param type
     * @param account
     * @return
     */
    public static int getCountsByKey(String tel, String type, String account){
        int value=0;
        try {
            pool= new JedisPool(new GenericObjectPoolConfig(),"115.28.170.190",6379,5000,"jesong123_123",dialStatsDB);
            Jedis jedis = pool.getResource();
            String key = type+account+tel;
            value= Integer.valueOf(jedis.get(key));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return value;
    }

    /**
     * 根据accountid 查询redis 计数
     * zh_N00000000143_*
     * @param accountId
     */
    public static List<Object> queryRedisByAccountId(String accountId){
        List<Object> redisList = new ArrayList<Object>();
        try {
            Jedis jedis = pool.getResource();
            //zh_N00000000143_13439236004
            Set keys = jedis.keys("zh_" + accountId + "_*");
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                String key = iterator.next().toString();
                String value = jedis.get(key);
                map.put("reids-key", "'" + key + "'");
                map.put("redis-value", "'" + value + "'");
                redisList.add(map);
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        System.out.println("redisList=="+redisList);
        return redisList;
    }
    
    
   /**
    * redis（递增）计数
    * @param key
    * @return
    */
   public  static int AddRedisIncr(String key, int seconds) {

	   System.out.println("seconds="+seconds);
	   	int value=0;
	   try {
           RedisAPI redisHandler = new RedisAPI(getDialStatsPool());
           Pipeline pipe = redisHandler.pipelined();
           pipe.multi();
           pipe.incr(key);
           //seconds != -1 时，设置生命周期，否则不设置周期为永久
           if(seconds != -1){
               pipe.expire(key, seconds);
           }
           Response res = pipe.get(key);
           pipe.exec();
           pipe.close();
           value =  Integer.parseInt(res.get().toString());


         //  log.info("redis_key="+key+",redis_value="+value);
           System.out.println("args = [" + value + "]");
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return value;
   }

   /**
    * 根据accountid 查询redis 计数
    * zh_N00000000143_*
    *
    */
   public static List<Object> queryRedis(){
       List<Object> redisList = new ArrayList<Object>();
       try {
    	   pool= new JedisPool(new GenericObjectPoolConfig(),"",6379,5000,"",2);
           Jedis jedis = pool.getResource();
         // System.out.println("redis value is "+ jedis.get("test_key_liuty"));
         //  AddRedisIncr("liuty",100);
            //jedis.flushDB();
           //zh_N00000000143_13439236004
           Set keys = jedis.keys("*");
           Iterator iterator = keys.iterator();
           while (iterator.hasNext()) {
               Map<String, Object> map = new HashMap<String, Object>();
               String key = iterator.next().toString();
               String value = jedis.get(key);
               System.out.println("key="+key);
               System.out.println("value="+value);
           }
       }catch (Exception e) {
           // TODO: handle exception
           e.printStackTrace();
       }
       return redisList;
   }

   public static void main(String[] args) {
	   queryRedis();
}



    public static String getRedisValueBySet(String key){
       String value ="";

       try{
           Jedis jedis = pool.getResource();
           value=  jedis.get(key);

       }catch (Exception e ){
            e.printStackTrace();
       }

       return value;
    }



}
