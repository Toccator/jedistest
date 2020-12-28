package FunctionInterfaceTest.Useing;

import org.junit.Test;

import java.util.function.BiConsumer;

public class ConsumerTest {

    @Test
    public void test1() {
        c(1, System.out::print);

        bc(1, 2, (i, j) -> System.out.print(
                Integer.parseInt(i.toString()) + Integer.parseInt(j.toString()) + " = "));
    }

    private void c(int i, Consumer consumer) {
        consumer.andThen(j -> System.out.println(" = " + j)).accept(i);

    }

    private void bc(int i, int j, BiConsumer biConsumer) {
        biConsumer.andThen((x, y) -> System.out.println(x +" + "+ y)).accept(i, j);
    }
}