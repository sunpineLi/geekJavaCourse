package geek.java.course.homework04;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 */
public class Solution04 {

    private static volatile int result = 0;

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        Thread thread = new Thread(() -> result = sum());

        thread.start();

        while (result == 0) {
            continue;
        }

        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
