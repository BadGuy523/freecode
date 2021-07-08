package com.zjq.freecode.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Configuration
public class ThreadPool {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6,6,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());

    public Future submit(Callable callable) {
        return threadPoolExecutor.submit(callable);
    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

}
