package FunctionInterfaceTest.Useing;


/**
 * 不需要参数，但返回一个结果，不吃光吐，
 * @param <T>
 */
@FunctionalInterface
public interface Supplier<T> {

    T get();
}

