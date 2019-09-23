package com.java.thread.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ExecutorTest {
    public static void main(String[] args) throws Exception {
        //threadPoolWithCallableTask();
        //threadPoolWithExecute();
        threadPoolWithSubmit();
    }

    private static void threadPoolWithCallableTask() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(2, 2, 0L, MILLISECONDS, new LinkedBlockingQueue<>());
        List<Callable<String>> workToDoList = new ArrayList<>();
        IntStream.range(1, 10)
                .forEach((i) -> {
                    workToDoList.add(() -> {
                        System.out.println("In callable for id " + i);
                        Thread.sleep(4000);
                        return "Task finished for id " + i;
                    });
                });
        List<Future<String>> futureList = executorService.invokeAll(workToDoList);
        System.out.println("InvokeAll method called .It ensures all callables are called till then it blocks the call ");
        futureList.forEach((future) -> {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
    /*
    Output
    In callable for id 1
    In callable for id 2
    In callable for id 3
    In callable for id 4
    In callable for id 5
    In callable for id 6
    In callable for id 7
    In callable for id 8
    In callable for id 9
    InvokeAll method called .It ensures all callables are called till then it blocks the call
    Task finished for id 1
    Task finished for id 2
    Task finished for id 3
    Task finished for id 4
    Task finished for id 5
    Task finished for id 6
    Task finished for id 7
    Task finished for id 8
    Task finished for id 9
     */

    private static void threadPoolWithExecute() {
        Executor executor = Executors.newFixedThreadPool(10);
        IntStream.range(1, 10)
                .forEach((i) -> {
                    executor.execute(() -> {
                        try {
                            if (i == 8) {
                                Thread.sleep(4000);
                            } else {
                                Thread.sleep(1000);
                            }
                            System.out.println("In runnable task for id -" + i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                });
        System.out.println("End of method .THis gets printed immdly since execute is not blocking call");
    }
        /*
        output -
        End of method .THis gets printed immdly since execute is not blocking call
        In runnable task for id -6
        In runnable task for id -9
        In runnable task for id -7
        In runnable task for id -1
        In runnable task for id -4
        In runnable task for id -3
        In runnable task for id -2
        In runnable task for id -5
        In runnable task for id -8
         */

    private static void threadPoolWithSubmit() throws InterruptedException, ExecutionException {
        Executor executor = Executors.newFixedThreadPool(1);
        Callable<String> callable=()->{
            try{
                Thread.sleep(4000);
                System.out.println("In callable");
                Thread.sleep(4000);
                System.out.println("In runnable for task id 1");
            }catch(Exception e){
                e.printStackTrace();
            }
            return "success";
        };
        Future<String> future =((ExecutorService) executor).submit(callable);
        System.out.println("Future result - "+future.get()+" Future is blocking as it blocks until result is obtained");
        System.out.println("End of method ");
        ((ExecutorService) executor).shutdown();
        /*Output
        In callable
        In runnable for task id 1
        Future result - success Future is blocking as it blocks until result is obtained
        End of method
         */
    }
}
