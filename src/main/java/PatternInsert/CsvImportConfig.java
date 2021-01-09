package PatternInsert;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.*;
import org.codehaus.jackson.annotate.JsonIgnore;

//        jackson.annotate.JsonIn

@Setter
@Getter
public class CsvImportConfig {
    private String operation = "SET";
    private String delimiter = ",";
    private String key ;
    private String field;
    private String value;

    @JsonIgnore
    private CsvImportVariable keyVariable;
    @JsonIgnore
    private CsvImportVariable fieldVariables;
    @JsonIgnore
    private CsvImportVariable valueVariable;


}
