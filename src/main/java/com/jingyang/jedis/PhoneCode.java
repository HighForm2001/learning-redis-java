package com.jingyang.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args){
        // simulate verification code send
        verify("01136184347");

//        getRedisCode("01136184347","890909");
    }

    // test
    public static void getRedisCode(String phone, String code){
        // get vcode from redis
        Jedis jedis = new Jedis("172.31.22.82",6379);
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        if(redisCode.equals(code)){
            System.out.println("success");
        }else{
            System.out.println("fail");
        }
        jedis.close();

    }

    // verification. One phone can only verify 3 times a day
    public static void verify(String phone){
        Jedis jedis = new Jedis("172.31.22.82",6379);

        // combine key
        String countKey = "VerifyCode" + phone + ":count";
        String codeKey = "VerifyCode" + phone + ":code";

        String count = jedis.get(countKey);
        if (count == null){
            // if no, means did not send before
            jedis.setex(countKey,24*60*60,"1");

        }else if(Integer.parseInt(count)<=2){
            jedis.incr(countKey);
        }else if (Integer.parseInt(count) > 2){
            System.out.println("send count more than 3");
            jedis.close();
            return;
        }

        String vCode = getCode();
        jedis.setex(codeKey,120,vCode);
        jedis.close();
    }
    // generate random code
    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i ++){
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }
}
