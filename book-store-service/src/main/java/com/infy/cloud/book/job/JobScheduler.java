package com.infy.cloud.book.job;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

public class JobScheduler {

    public static void startScheduler()
    {
        Properties props = new Properties();
        props.put("org.quartz.threadPool.threadCount","1");
        try {
            StdSchedulerFactory factory = new StdSchedulerFactory(props);
            factory.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
