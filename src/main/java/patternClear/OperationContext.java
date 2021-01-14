package patternClear;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;

@Setter
@Builder
@Getter
public class OperationContext {
    private String name;
    private JedisPool jedisPool;
    private ScanParams scanParams;
    private ScanClearParam scanClearParam;
    private String zsetPath;
    private String hashPath;


}
