package jedis.toMapTest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
class Book {
    private String name;
    private int releaseYear;
    private String isbn;

    // getters and setters
    public String getName() {
        return name;
    }
    public String getIsbn(){
        return isbn;
    }
    public int getReleaseYear(){
        return releaseYear;
    }

}