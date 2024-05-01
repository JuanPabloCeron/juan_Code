package com.jpceron.CarRegistry.config;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@ExtendWith(MockitoExtension.class)
class AsycConfigTest {

    @InjectMocks
    AsyncConfig asyncConfig;


    @Test
    void test_taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("CarRegistry");
        executor.initialize();

        assertEquals(5,executor.getCorePoolSize());
        assertEquals(10,executor.getMaxPoolSize());
        assertEquals(100,executor.getQueueCapacity());
        assertEquals("CarRegistry",executor.getThreadNamePrefix());

    }

}
