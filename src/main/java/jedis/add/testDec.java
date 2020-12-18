package jedis.add;

import java.text.DecimalFormat;

public class testDec {
    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");
        System.out.println(decimalFormat.format(3/2));
    }
}
