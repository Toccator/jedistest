package jedis.multiThread;

public class SynchronizedDemo implements Runnable{

    static int i = 0;
    // 作用于实例方法
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
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        Thread t1 = new Thread(synchronizedDemo);
        Thread t2 = new Thread(synchronizedDemo);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}