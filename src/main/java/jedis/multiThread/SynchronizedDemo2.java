package jedis.multiThread;

public class SynchronizedDemo2 implements Runnable{
    static int i = 0;

    public synchronized void increase(){
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
        SynchronizedDemo2 synchronizedDemo = new SynchronizedDemo2();
        SynchronizedDemo2 synchronizedDemo2 = new SynchronizedDemo2();
        Thread t1 = new Thread(synchronizedDemo);
        Thread t2 = new Thread(synchronizedDemo2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
