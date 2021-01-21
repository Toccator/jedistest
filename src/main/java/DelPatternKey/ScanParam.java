package DelPatternKey;

import FunctionInterfaceTest.Useing.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
public class ScanParam {
    private Predicate<String> predicate;
    private String searchPattern;
    private String redisConfig;
    private String path;
    private String fileCount;
    private String fileName;
    private Integer searchCount;
    private String bacFileName;
    private String bacFilePath;
    private Boolean writeInLocal;
    private int writeIndex;
}
