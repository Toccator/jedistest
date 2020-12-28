package FunctionInterfaceTest;


/**
 * zcy
 * 假设有一个 方法使用该函数式接口作为参数，那么就可以使用Lambda进行传参
 */

public class Test_Functional {
    // 定义一个含有函数式接口的方法
    //方法使用该函数式接口作为参数
    public static void doSomthing(MyFunctionalInterface functionalInterface) {
        functionalInterface.method();//调用自定义函数式接口的方法
    }
    public static void main(String[] args) {
        //调用函数式接口的方法
        doSomthing(()->System.out.println("excuter lambda!"));
    }
}

