package houzm.accumulation.redis.sort;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/18 15:20
 * description: redis sortset zcard
 *
 * ZCARD key
 * 返回有序集 key 的基数。
 * 可用版本：
 *      >= 1.2.0
 * 时间复杂度:
 *      O(1)
 * 返回值:
 *      当 key 存在且是有序集类型时，返回有序集的基数。
 *      当 key 不存在时，返回 0 。
 */
public class Zcard {

    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        String key = "zcard-test";
        jedis.del(key);
        //当 key 不存在时，返回 0 。
        System.out.println("sortset-zcard: " + jedis.zcard(key)); //0
        //当 key 存在且是有序集类型时，返回有序集的基数。
        System.out.println("sortset-zadd-single: " + jedis.zadd(key, 30, "30-member")); //1
        System.out.println("sortset-zcard: " + jedis.zcard(key)); //1
        System.out.println("sortset-zadd-single: " + jedis.zadd(key, 20, "20-member")); //1
        System.out.println("sortset-zcard: " + jedis.zcard(key)); //2
        System.out.println("sortset-zadd-single: " + jedis.zadd(key, 10, "10-member")); //1
        System.out.println("sortset-zcard: " + jedis.zcard(key)); //3


        //当 key 存在但不是有序集类型时，抛出异常。
        jedis.del(key);
        System.out.println("string-set: " + jedis.set(key, "test-memeber"));
        //Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException:
        // WRONGTYPE Operation against a key holding the wrong kind of value
        System.out.println("sortset-zcard: " + jedis.zcard(key));

    }

}
