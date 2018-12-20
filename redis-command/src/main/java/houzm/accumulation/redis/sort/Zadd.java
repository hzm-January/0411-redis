package houzm.accumulation.redis.sort;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/18 14:28
 * description: redis sortset zadd
 *
 * ZADD key score member [[score member] [score member] ...]
 * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
 * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
 * score 值可以是整数值或双精度浮点数。
 * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
 * 当 key 存在但不是有序集类型时，返回一个错误。
 *
 * 在 Redis 2.4 版本以前， ZADD 每次只能添加一个元素。
 * 可用版本：
 *      >= 1.2.0
 * 时间复杂度:
 *      O(M*log(N))， N 是有序集的基数， M 为成功添加的新成员的数量。
 * 返回值:
 *      被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
 *
 */
public class Zadd {
    public static void main(String[] args) {
        // pre
        Jedis jedis = RedisUtil.getJedis();
        String key = "zadd-test";
        jedis.del(key);
        //zadd--key存在但不是sortset类型--添加单个元素
        System.out.println("string-set-添加单个元素："+jedis.set(key, "test-exists-key-zadd"));
        // Exception in thread "main" redis.clients.jedis.exceptions.JedisDataException:
        // WRONGTYPE Operation against a key holding the wrong kind of value
//        System.out.println("sortset-zadd-添加单个元素: "+jedis.zadd(key, 1, "1-member"));
        jedis.del(key);

        //zadd--key不存在--添加单个元素
        System.out.println("sortset-zadd-添加单个元素：" + jedis.zadd(key, 30, "30-member")); //1
        System.out.println("sortset-zadd-添加单个元素：" + jedis.zadd(key, 20, "20-member")); //1
        System.out.println("sortset-zadd-添加单个元素：" + jedis.zadd(key, 10, "10-member")); //1
        System.out.println("sortset-zadd-打印所有元素：" + jedis.zrange(key, 0, -1)); //[10-member, 20-member, 30-member]

        //zadd--key存在并且是sortset类型--添加多个元素
        Map<String, Double> maps = new HashMap<>(5);
        maps.put("55-member", 55D);
        maps.put("50-member", 50D);
        System.out.println("sortset-zadd-添加多个元素：" + jedis.zadd(key, maps)); //2
        System.out.println("sortset-zadd-打印所有元素：" + jedis.zrange(key, 0, -1)); //[10-member, 20-member, 30-member, 50-member, 55-member]

        //zadd--key存在并且是sortset类型--将要添加的元素已存在
        System.out.println("sortset-zadd-修改单个元素score：" + jedis.zadd(key, 40, "10-member")); //0
        System.out.println("sortset-zadd-打印所有元素：" + jedis.zrange(key, 0, -1)); //[20-member, 30-member, 10-member, 50-member, 55-member]


        //zadd--key存在并且是sortset类型--添加多个元素--部分元素已存在
        Map<String, Double> mapsModifyScore = new HashMap<>(5);
        mapsModifyScore.put("55-member", 35D);
        mapsModifyScore.put("50-member", 45D);
        mapsModifyScore.put("60-member", 60D);
        mapsModifyScore.put("70-member", 70D);
        mapsModifyScore.put("80-member", 80D);
        System.out.println("sortset-zadd-添加多个元素：" + jedis.zadd(key, mapsModifyScore)); //3
        System.out.println("sortset-zadd-打印所有元素：" + jedis.zrange(key, 0, -1)); //[20-member, 30-member, 55-member, 10-member, 50-member, 60-member, 70-member, 80-member]

    }
}
