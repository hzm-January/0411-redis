package houzm.accumulation.lua.command;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/20 16:25
 * description:
 */
public class Evalsha {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        //1. eval
        String key = "houzm-lua";
        String value = "houzm-lua-value";
        jedis.del(key);
        jedis.set(key, value);
        String luaScript = "return redis.call('get', KEYS[1])";
        Object eval = jedis.eval(luaScript, 1, key);
        System.out.println("eval获取key的value: " + eval); //houzm-lua-value
        //2. script
        //2.1 script load
        String sha = jedis.scriptLoad(luaScript); //获取脚本的sha值
        System.out.println("redis使用Lua脚本-script load: " + sha); //4e6d8fc8bb01276962cce5371fa795a7763657ae
        System.out.println("redis使用Lua脚本-script exists: " + jedis.scriptExists(sha)); //true
//        System.out.println("redis使用Lua脚本-script flush: " + jedis.scriptFlush());
        //2. evalsha
        System.out.println("evalsha获取key: "+jedis.evalsha(sha, 1, key));
    }
}
