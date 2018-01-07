package com.dijkspicy.gambol;

import com.dijkspicy.gambol.model.Request;
import com.dijkspicy.gambol.model.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * gambol
 *
 * @author dijkspicy
 * @date 2018/1/7
 */
public class RequestHandlerTest {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    @Test
    public void test1() throws Exception {
        Request e = newRequest();
        RequestQueue.ins.getRequestQueue().add(e);

        new RequestHandler().start();

        this.countDownLatch.await(10, TimeUnit.SECONDS);
    }

    private Request newRequest() {
        Task a = new Task().setId("a");
        Task b = new Task().setId("b");
        Task c = new Task().setId("c");
        Task d = new Task().setId("d");
        Task e = new Task().setId("e");
        Task f = new Task().setId("f");
        a.addDependency(Arrays.asList(b,c));
        b.addDependency(e);
        c.addDependency(d);
        d.addDependency(e);

        return new Request()
                .setTasks(Arrays.asList(a,b,c,d,e,f));
    }
}