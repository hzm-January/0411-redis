package houzm.accumulation.redis.sort;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/18 15:31
 * description: redis sortset zcount
 *
 * ZCOUNT key min max
 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
 * 关于参数 min 和 max 的详细使用方法，请参考 ZRANGEBYSCORE 命令。
 *
 * 可用版本：
 *      >= 2.0.0
 * 时间复杂度:
 *      O(log(N))， N 为有序集的基数。
 * 返回值:
 *      score 值在 min 和 max 之间的成员的数量。
 */
public class Zcount {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        String key = "zcount-test";
        jedis.zadd(key, 30, "30-member");
        jedis.zadd(key, 20, "20-member");
        jedis.zadd(key, 21, "21-member");
        jedis.zadd(key, 22, "22-member");
        jedis.zadd(key, 10, "10-member");
        System.out.println("sortset-all-element: " + jedis.zrangeByScore(key, "-inf", "+inf")); //[10-member, 20-member, 21-member, 22-member, 30-member]
        // 如果想获取所有元素，但是不知道分数的min和max，可以使用-inf, +inf
        System.out.println("sortset-zcount：" + jedis.zcount(key, "-inf", "+inf")); //5
        // 获取指定分数区间中的元素
        System.out.println("sortset-zcount：" + jedis.zcount(key, 20, 30)); //4
    }
}
