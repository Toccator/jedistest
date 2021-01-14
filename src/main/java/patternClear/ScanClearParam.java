package patternClear;

import FunctionInterfaceTest.Useing.Predicate;
import lombok.Builder;
import lombok.Setter;


@Builder
@lombok.Setter
@lombok.Getter
public class ScanClearParam {
    private String searchPattern;
    private int searchCount;
    private int syncCount;
    private Predicate<String> predicate;
    private boolean random;
    private String zsetFileName;
    private String hashFileName;
    private String model;
    @Setter
    private int randomCount;
}
