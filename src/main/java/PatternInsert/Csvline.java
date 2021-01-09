package PatternInsert;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Csvline {
    private String key;
    private String field;
    private String value;
}
