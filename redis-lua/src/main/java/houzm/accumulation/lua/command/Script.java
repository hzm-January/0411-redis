package houzm.accumulation.lua.command;

import houzm.accumulation.common.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/20 16:25
 * description:
 */
public class Script {
    public static void main(String[] args) {
        Jedis jedis = RedisUtil.getJedis();
        //1. eval
        String luaScript = "return redis.call('get', KEYS[1])";
        //2. script
        //2.1 script load 将脚本保存到脚本缓存，并返回sha摘要值
        String sha = jedis.scriptLoad(luaScript); //获取脚本的sha值
        System.out.println("redis使用Lua脚本-script load: " + sha); //4e6d8fc8bb01276962cce5371fa795a7763657ae
        //2.2 script exists 判断脚本是否在脚本缓存中，在true，不在false
        System.out.println("redis使用Lua脚本-script exists: " + jedis.scriptExists(sha)); //true
        //2.3 script flush 清空缓存
        System.out.println("redis使用Lua脚本-script flush: " + jedis.scriptFlush()); //OK
        //2.4 script kill 停止当前正在执行的Lua脚本
        System.out.println("redis使用Lua脚本-script kill: " + jedis.scriptKill()); //redis.clients.jedis.exceptions.JedisDataException: NOTBUSY No scripts in execution right now.
    }
}
