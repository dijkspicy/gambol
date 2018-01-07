package com.dijkspicy.gambol;

import com.dijkspicy.gambol.model.Batch;
import com.dijkspicy.gambol.model.Job;
import com.dijkspicy.gambol.model.Request;
import com.dijkspicy.gambol.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/6
 */
public class RequestHandler extends Thread {
    public RequestHandler() {
        super(() -> {
            System.out.println("start");
            long start = System.currentTimeMillis();
            while (true) {
                Request request = RequestQueue.ins.getRequestQueue().poll();
                if (request == null) {
                    break;
                }

                Queue<Batch> batches = request.batch();
                execute(batches);
            }
            System.out.println(System.currentTimeMillis() - start);
        });
    }

    private static void execute(Queue<Batch> batches) {
        while (!batches.isEmpty()) {
            Batch batch = batches.poll();
            System.out.println(batch);
            List<Future<?>> futures = new ArrayList<>();
            batch.getJobs().parallelStream().forEach(job -> {
                Future<?> future = execute(job);
                futures.add(future);
            });
            futures.forEach(future -> {
                try {
                    Object out = future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static Future<?> execute(Job job) {
        return Executors.newSingleThreadExecutor().submit(() -> {
            Queue<Task> tasks = job.getTasks();
            StringBuilder sb = new StringBuilder();
            while (!tasks.isEmpty()) {
                sb.append(tasks.poll());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
            System.out.println(sb);
            return LocalDateTime.now();
        });
    }
}
