package fiveguys.Tom.Cafeteria.Server.domain.common;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String value){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    public void setValue(String key, String value, Duration duration){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

    public String getValue(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (String) values.get(key);
    }

    public void deleteValues(String key){
        redisTemplate.delete(key);
    }
}
