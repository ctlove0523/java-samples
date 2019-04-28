package io.github.ctlove0523.javasamples.retry;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: RetryService
 *
 * @author: chentong
 * Date:     2019/4/21 13:48
 */
public class RetryService {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final List<String> STATES = Arrays.asList("inprogress", "completed");
    private static final Random random = new Random();
    private static final Predicate predicat = o -> {
        List<UserState> userStates = (List<UserState>) o;
        for (UserState userState : userStates) {
            if ("inprogress".equalsIgnoreCase(userState.getState())) {
                return true;
            }
        }
        return false;

    };
    private static RetryConfig config = RetryConfig.custom()
            .maxAttempts(2)
            .waitDuration(Duration.ofMillis(getUserCounts()*1000))
            .retryOnResult(predicat)
            .build();

    private static Retry retry = Retry.of("sync retry", config);


    public static void main(String[] args) throws Exception {

        List<UserState> result = Retry.decorateCallable(retry, new Callable<List<UserState>>() {
            @Override
            public List<UserState> call() throws Exception {
                List<UserState> userStates = getUserStates();
                return userStates;
            }
        }).call();
    }
    public static int getUserCounts() {
        return 3;
    }

    public static List<UserState> getUserStates() {
        System.out.println(Thread.currentThread().getName());
        System.out.println(System.currentTimeMillis() + "  getUserStates");
        List<UserState> userStates = new ArrayList<>(3);
        UserState userState1 = new UserState();
        userState1.setName("A");
        userState1.setState(STATES.get(Math.abs(random.nextInt() % 2)));
        userStates.add(userState1);

        UserState userState2 = new UserState();
        userState2.setName("B");
        userState2.setState(STATES.get(Math.abs(random.nextInt() % 2)));
        userStates.add(userState2);


        UserState userState3 = new UserState();
        userState3.setName("C");
        userState3.setState(STATES.get(Math.abs(random.nextInt() % 2)));
        userStates.add(userState3);

        return userStates;
    }
}
