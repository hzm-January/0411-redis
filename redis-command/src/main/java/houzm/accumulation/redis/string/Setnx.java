package houzm.accumulation.redis.string;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/20 17:36
 * description: redis string setnx
 * SETNX key value
 * 将 key 的值设为 value ，当且仅当 key 不存在。
 * 若给定的 key 已经存在，则 SETNX 不做任何动作。
 * SETNX 是『SET If Not EXists』(如果不存在，则 SET)的简写。
 * 可用版本：
 *      >= 1.0.0
 * 时间复杂度：
 *      O(1)
 * 返回值：
 *      设置成功，返回 1
 *      设置失败，返回 0
 */
public class Setnx {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        String key = "test-setnx";
        String value = "test-setnx-value";
        jedis.del(key);
        System.out.println("redis-string-exists key是否存在："+ jedis.exists(key)); //false
        System.out.println("redis-string-setnx key不存在："+ jedis.setnx(key, value)); //1
        System.out.println("redis-string-exists key是否存在："+ jedis.exists(key)); //true
        System.out.println("redis-string-get 获取key的value："+ jedis.get(key)); //test-setnx-value
        System.out.println("redis-string-setnx key已存在："+ jedis.setnx(key, value)); //0
        System.out.println("redis-string-get 获取key的value："+ jedis.get(key)); //test-setnx-value
        RedisUtil.shutdown();
    }
}
