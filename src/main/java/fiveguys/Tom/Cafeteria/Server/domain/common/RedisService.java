package fiveguys.Tom.Cafeteria.Server.domain.common;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public boolean tryLock(String key, String value, long timeout, TimeUnit unit) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return success != null && success;
    }

    public void setValue(String key, String value){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    public void setValue(String key, String value, Duration duration){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, Duration.ofSeconds(1));
    }

    public String getValue(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        return (String) values.get(key);
    }

    public void deleteValues(String key){
        redisTemplate.delete(key);
    }
}
