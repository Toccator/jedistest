package FunctionInterfaceTest.Useing;

import org.junit.Test;

import java.util.function.IntSupplier;

public class SupplierTest {

    @Test
    public void test1() {
        s(() -> "hello");
        is(() -> 1);
    }



    private void s(Supplier supplier) {
        System.out.println(supplier.get());
    }

    private void is(IntSupplier intSupplier) {
        System.out.println(intSupplier.getAsInt() * 2);
    }
}

