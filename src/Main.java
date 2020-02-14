import static java.lang.Thread.sleep;

//Demo to show waiting thread can be interrupted
public class Main {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");
        Main m = new Main();
        Runnable thread = new Thread(new ThreadX(m));
        ((Thread) thread).start();
        Thread.sleep(4000);
        ((Thread) thread).interrupt();
    }
}

class ThreadX implements Runnable {
    Main sharedObj;

    public ThreadX(Main sharedObj) {
        this.sharedObj = sharedObj;
    }

    @Override
    public void run() {
        synchronized (sharedObj) {
            try {
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Trying to run thread !");
                    sleep(1000);
                    System.out.println("getting in interrupted state");
                    sharedObj.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/*Output
 Hello World!
Trying to run thread !
getting in interrupted state
java.lang.InterruptedException
	at java.lang.Object.wait(Native Method)
	at java.lang.Object.wait(Object.java:502)
	at ThreadX.run(Main.java:31)
	at java.lang.Thread.run(Thread.java:745)

 */