package com.example.demo;

import com.github.rholder.retry.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by mc on 2018/3/6.
 */
public class RetryTest {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    public static void main(String[] args) {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                //.retryIfException()
                .retryIfExceptionOfType(IOException.class)
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <Boolean> void onRetry(Attempt<Boolean> attempt) {
                        // 第几次重试,(注意:第一次重试其实是第一次调用)
                        System.out.print("[retry]time=" + attempt.getAttemptNumber());

                        // 距离第一次重试的延迟
                        System.out.print(",delay=" + attempt.getDelaySinceFirstAttempt());

                        // 重试结果: 是异常终止, 还是正常返回
                        System.out.print(",hasException=" + attempt.hasException());
                        System.out.print(",hasResult=" + attempt.hasResult());

                        // 是什么原因导致异常
                        if (attempt.hasException()) {
                            System.out.print(",causeBy=" + attempt.getExceptionCause().toString());
                        } else {
                            // 正常返回时的结果
                            System.out.print(",result=" + attempt.getResult());
                        }

                        // bad practice: 增加了额外的异常处理代码
                        try {
                            Boolean result = attempt.get();
                            System.out.print(",rude get=" + result);
                        } catch (ExecutionException e) {
                            System.err.println("this attempt produce exception." + e.getCause().toString());
                        }

                        System.out.println();
                    }
                })
                .build();
        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(buildTask());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end..." + df.format(new Date()));
    }

    private static Callable<Boolean> buildTask() {
        return new Callable<Boolean>() {
            private int i = 0;

            @Override
            public Boolean call() throws Exception {
                System.out.println("called");
                i++;
                if (i == 1) {
                    throw new IOException();
                } else {
                    throw new NullPointerException();
                }
            }
        };
    }

}
