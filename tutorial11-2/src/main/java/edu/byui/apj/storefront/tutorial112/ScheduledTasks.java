package edu.byui.apj.storefront.tutorial112;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 21 3 * * *")
    public void processNames() throws InterruptedException {
        List<String> names = Arrays.asList(
                "Alice", "Bob", "Charlie", "David", "Emma",
                "Frank", "Grace", "Henry", "Ivy", "Jack",
                "Karen", "Liam", "Mia", "Noah", "Olivia",
                "Paul", "Quinn", "Ryan", "Sophia", "Thomas"
        );
        // split into two batches
        List<String> batch1 = names.subList(0, 10);
        List<String> batch2 = names.subList(10, 20);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<Void>> jobs = new LinkedList<>();
        jobs.add(() -> {
            batch1.forEach(log::info);
            return null;
        });
        jobs.add(() -> {
            batch2.forEach(log::info);
            return null;
        });
        executorService.invokeAll(jobs);
        log.info("All done here!");
        executorService.shutdown();
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
