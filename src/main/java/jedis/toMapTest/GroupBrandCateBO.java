package jedis.toMapTest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Builder
@Setter
@Getter
public class GroupBrandCateBO implements Serializable {
    @Setter
    private String a;
    @Setter
    private String b;
    @Setter
    private String c;



    public String getVersion() {
        return a;
    }
    public Object getGroupCode() {
        return b;
    }
}
