package DataStructure.base.toMapTest;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookList {
//    private static Object List;
    public static void testToMap(String[] args) {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Ring", 1954, "0395489318"));
        bookList.add(new Book("Towers", 1954, "0345339711"));
        bookList.add(new Book("King", 1955, "0618129111"));


        bookList.stream().sorted(Comparator.comparing(Book::getName))
                .collect(Collectors.toMap(Book::getName, Function.identity(), (o1, o2) -> o1, TreeMap::new));

//        System.out.println(bookList);


        String name = "Towers";
        String isbn = "0345339711";
        int releaseYear = 0;
        Book book = Book.builder().name(name).isbn(isbn).releaseYear(releaseYear).build();
        System.out.println(book);


//        return bookList.get();
    }
    public static void main(String[] args) {
        testToMap(args);
    }

}
