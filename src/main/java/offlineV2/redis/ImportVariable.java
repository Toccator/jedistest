package offlineV2.redis;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Builder
@Slf4j
@Setter
@Getter
public class ImportVariable implements Serializable {

    private final String formatString;

    private final List<String> fields;

    @Override
    public String toString(){
        return String.format(formatString,fields.toArray(new Object[0]));
    }
}
