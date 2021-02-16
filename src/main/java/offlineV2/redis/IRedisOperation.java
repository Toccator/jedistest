package offlineV2.redis;

import com.mysql.cj.result.Row;
import offlineV2.util.JedisClusterPipeline;

import java.io.Serializable;

public interface IRedisOperation extends Serializable {

    void doRedisOperation(Row row , JedisClusterPipeline jedisClusterPipeline);

}
