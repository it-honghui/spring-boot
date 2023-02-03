package com.example.demo.config;import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.scheduling.TaskScheduler;import org.springframework.scheduling.annotation.AsyncConfigurer;import org.springframework.scheduling.annotation.EnableScheduling;import org.springframework.scheduling.annotation.SchedulingConfigurer;import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;import org.springframework.scheduling.config.ScheduledTaskRegistrar;import java.util.concurrent.Executor;/** * @author honghui 2022/3/14 */@Configuration@EnableSchedulingpublic class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {  @Override  public Executor getAsyncExecutor() {    return taskScheduler();  }  @Override  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {    return new SimpleAsyncUncaughtExceptionHandler();  }  @Override  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {    TaskScheduler taskScheduler = taskScheduler();    taskRegistrar.setTaskScheduler(taskScheduler);  }  @Bean(destroyMethod = "shutdown")  public ThreadPoolTaskScheduler taskScheduler() {    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();    scheduler.setPoolSize(20);    scheduler.setThreadNamePrefix("task-");    scheduler.setAwaitTerminationSeconds(60);    scheduler.setWaitForTasksToCompleteOnShutdown(true);    return scheduler;  }}