package houzm.accumulation.redis.controller;

import houzm.accumulation.redis.pojo.PoJo;
import java.io.IOException;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Author: hzm_dream@163.com
 * Date:  2018/11/21 17:06
 * Modified By:
 * Description：
 */
@RestController
@RequestMapping(value = "/api/")
public class RedisController {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @RequestMapping(value = "save")
    public void save() {
        String key = "123456789";
        String value = "正就是ljlasdjfljsaf";
        PoJo poJo = new PoJo();
        poJo.setId("123123");
        poJo.setName("hozm");
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                try {
                    redisConnection.set(redisTemplate.getStringSerializer().serialize(key), new ObjectMapper().writeValueAsString(poJo).getBytes());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
    @RequestMapping(value = "get/{key}")
    public PoJo get(@PathVariable(value = "key") String key) {
        PoJo execute = redisTemplate.execute(new RedisCallback<PoJo>() {
            @Override
            public PoJo doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keybytes = redisTemplate.getStringSerializer().serialize(key);
                if (redisConnection.exists(keybytes)) {
                    byte[] valuebytes = redisConnection.get(keybytes);
                    PoJo poJo = null;
                    try {
                        poJo = new ObjectMapper().readValue(new String(valuebytes), PoJo.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return poJo;
                }
                return null;
            }
        });
        System.out.println(execute);
        return execute;
    }

}
