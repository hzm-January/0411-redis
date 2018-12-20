package houzm.accumulation.lua.command;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/20 16:25
 * description:
 */
public class Eval {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        //1. eval
        String key = "houzm-lua";
        String value = "houzm-lua-value";
        jedis.del(key);
        String luaScript = "return redis.call('set', KEYS[1], ARGV[1])";
        Object eval = jedis.eval(luaScript, 1, key, value);
        System.out.println("eval获取key-value: " + eval); //OK
        System.out.println("string-get获取key的value："+ jedis.get(key)); //houzm-lua-value

    }
}
