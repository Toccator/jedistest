package offlineV2;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SparkTempTable {
    private final String tableName;

    private final List<String> hdfsFilePaths;

    private final boolean isNeedCache;

}
