package com.jpceron.CarRegistry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);//Indica el numero de hilos que se podran ejecutar
        executor.setMaxPoolSize(10);//Indica el maximo de hilos que se ejecutan a la vez
        executor.setQueueCapacity(100);//La capacidad de hilos en la cola
        executor.setThreadNamePrefix("CarRegistryThread-");//Nombre del hilo
        executor.initialize();
        return executor;

    }

}
