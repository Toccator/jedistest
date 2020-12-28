package FunctionInterfaceTest.Useing;

import java.util.Objects;

/**
 * 接受一个参数，但没有返回值，只吃不吐
 * @param <T>
 */


@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}

