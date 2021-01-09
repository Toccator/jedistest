package PatternInsert;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CsvImportVariable {
    private String formatString;
    private List<String> fields;
}
