package jedis.multiThread;

public class SynchronizedDemo3 implements Runnable{
    static int i = 0;
    // 作用静态方法上
    public static synchronized void increase(){
        i++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 作用于两个对象
        SynchronizedDemo3 synchronizedDemo = new SynchronizedDemo3();
        SynchronizedDemo3 synchronizedDemo2 = new SynchronizedDemo3();
        Thread t1 = new Thread(synchronizedDemo);
        Thread t2 = new Thread(synchronizedDemo2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
