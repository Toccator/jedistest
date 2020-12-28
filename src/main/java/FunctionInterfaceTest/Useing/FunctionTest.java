package FunctionInterfaceTest.Useing;

import org.junit.Test;

public class FunctionTest {

    @Test
    public void test1() {
        f(1
                , i -> Integer.parseInt(i.toString()) - 1
                , i -> Integer.parseInt(i.toString()) * 2);

    }

    public void f(int i, Function f1, Function f2) {
        System.out.println(f1.compose(f2).apply(i));

        System.out.println(f1.andThen(f2).apply(i));
    }
}

