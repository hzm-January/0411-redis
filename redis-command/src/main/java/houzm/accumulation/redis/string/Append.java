package houzm.accumulation.redis.string;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/18 13:28
 * description: redis String append
 *
 * APPEND key value
 *      如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
 *      如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
 * 可用版本：>= 2.0.0
 * 时间复杂度：平摊O(1)
 * 返回值：追加 value 之后， key 中字符串的长度。
 *
 * 时间序列
 * APPEND 可以为一系列定长(fixed-size)数据(sample)提供一种紧凑的表示方式，通常称之为时间序列。
 * 每当一个新数据到达的时候，执行以下命令：
 *      APPEND timeseries "fixed-size sample" （例如：APPEND ts "00 43"）
 * 然后可以通过以下的方式访问时间序列的各项属性：
 *
 * STRLEN 给出时间序列中数据的数量
 * GETRANGE 可以用于随机访问。只要有相关的时间信息的话，我们就可以在 Redis 2.6 中使用 Lua 脚本和 GETRANGE 命令实现二分查找。
 * SETRANGE 可以用于覆盖或修改已存在的的时间序列。
 *
 * 这个模式的唯一缺陷是我们只能增长时间序列，而不能对时间序列进行缩短，
 * 因为 Redis 目前还没有对字符串进行修剪(tirm)的命令，但是，不管怎么说，这个模式的储存方式还是可以节省下大量的空间。
 * 可以考虑使用 UNIX 时间戳作为时间序列的键名，这样一来，可以避免单个 key 因为保存过大的时间序列而占用大量内存，另一方面，也可以节省下大量命名空间。
 */
public class Append {
    private static Jedis jedis= RedisUtil.getJedis();

    public static void main(String[] args) throws InterruptedException {
        String key = "skin";
        String value = "skin-value";
        jedis.del(key);
        //append 追加操作测试
        System.out.println("redis-string-append-key不存在：" + jedis.append(key, value)); //10
//        System.out.println("key是否存在：" + jedis.exists(key)); //false
//        System.out.println("append " + key + " : " + jedis.append(key, "myskin")); //6
//        System.out.println("key："+key + " value:" + jedis.get(key)); //myskin
//        System.out.println("append " + key + " : " + jedis.append(key, "--after")); //13
//        System.out.println("key："+key + " value: " + jedis.get(key)); //myskin--after


        //时间序列
        String timeSeries = "TimeSeries";
        jedis.del(timeSeries);
        int fixedSize = String.valueOf(System.currentTimeMillis()).length();
        System.out.println("时间序列-TimeSeries定长 : " + fixedSize); //13
        System.out.println("时间序列-append : " + jedis.append(timeSeries, String.valueOf(System.currentTimeMillis()))); //13
        TimeUnit.SECONDS.sleep(1);
        System.out.println("时间序列-append : " + jedis.append(timeSeries, String.valueOf(System.currentTimeMillis()))); //26
        TimeUnit.SECONDS.sleep(1);
        System.out.println("时间序列-append : " + jedis.append(timeSeries, String.valueOf(System.currentTimeMillis()))); //39
        System.out.println("时间序列-getrange: " + jedis.getrange(timeSeries, 0, fixedSize - 1)); //1545113170376
        System.out.println("时间序列-getrange: " + jedis.getrange(timeSeries, fixedSize, 2 * fixedSize - 1)); //1545113171388
        System.out.println("时间序列-get：" + jedis.get(timeSeries)); //1545113170376154511317138815451131724061545113269459154511327137115451132723801545113505415
        System.out.println("时间序列-setrange: "+jedis.setrange(timeSeries, fixedSize, String.valueOf(System.currentTimeMillis()))); //91
        System.out.println("时间序列-get：" + jedis.get(timeSeries)); //1545113170376154511350640015451131724061545113269459154511327137115451132723801545113505415
    }

}
