package BUMIL.Secondhand_Library.domain.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
class BookAsyncConfig {
    @Bean(name = "bookApiTaskExecutor")
    public ThreadPoolTaskExecutor bookApiTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); //스레드 코어 수
        executor.setMaxPoolSize(3); //최대 스레드 풀 수
        executor.setQueueCapacity(1000); //큐의 크기
        executor.setThreadNamePrefix("APIClient-"); //log 확인용
        executor.initialize();
        return executor;
    }
}