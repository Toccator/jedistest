package FunctionInterfaceTest.Useing;

import org.junit.Test;

public class PredicateTest {

    @Test
    public void test1() {
        p(1, i -> Integer.parseInt(i.toString()) > 0, i -> Integer.parseInt(i.toString()) < 0);

        System.out.println("===========================================");

        System.out.println(Predicate.not(Predicate.isEqual("0")).test("1")); // true
    }

    private void p(int i, Predicate p1, Predicate p2) {
        System.out.println(p1.test(i)); // true
        System.out.println(p2.test(i)); // false

        System.out.println(p1.and(p2).test(i)); // false
        System.out.println(p1.or(p2).test(i)); // true
        System.out.println(p1.negate().test(i)); // false

    }
}

