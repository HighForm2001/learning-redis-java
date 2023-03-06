package com.jingyang.jedis;

import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedisDemo1 {

    public static void main(String[] args){
        Jedis jedis = new Jedis("172.31.22.82",6379);

        // test
        String ping = jedis.ping();
        System.out.println(ping);

    }

    // test key string
    @Test
    public void demo1(){
        Jedis jedis = new Jedis("172.31.22.82",6379);
        Set<String> keys = jedis.keys("*");
        jedis.set("name","luc");

        // get
        String name = jedis.get("name");
        System.out.println(name);

        // set many values
        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1","k2");
        System.out.println(mget);
        for(String key: keys){
            System.out.println(key);
        }
    }

    @Test
    public void demo2(){
        Jedis jedis = new Jedis("172.31.22.82",6379);
        jedis.lpush("key1","lucy","mary","jack");
        List<String> lrange = jedis.lrange("key1",0,-1);
        System.out.println(lrange);
    }

    @Test
    public void demo3(){
        Jedis jedis = new Jedis("172.31.22.82",6379);
        jedis.sadd("names","lucy");
        jedis.sadd("names","mary");
        Set<String> names=  jedis.smembers("names");
        System.out.println(names);
    }

    @Test
    public void demo4(){
        Jedis jedis = new Jedis("172.31.22.82",6379);
        jedis.hset("users","age","20");
        String hget = jedis.hget("users","age");
        System.out.println(hget);
    }

    @Test
    public void demo5(){
        Jedis jedis = new Jedis("172.31.22.82",6379);
        jedis.zadd("china",100,"shanghai");
        jedis.zadd("china",200,"chognqing");
        Set<String> zset = jedis.zrange("china",0,-1);
        System.out.println(zset);
    }
}
