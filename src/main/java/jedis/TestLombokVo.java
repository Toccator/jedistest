package jedis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TestLombokVo {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {
        private Long id;
        private String userName;
        private String password;
        private String name;
        private Integer age;
        private String email;
    }
}
