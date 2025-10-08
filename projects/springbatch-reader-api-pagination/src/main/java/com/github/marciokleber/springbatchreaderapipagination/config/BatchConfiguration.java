package com.github.marciokleber.springbatchreaderapipagination.config;

import com.github.marciokleber.springbatchreaderapipagination.batch.PaginatedApiItemReader;
import com.github.marciokleber.springbatchreaderapipagination.model.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    @Value("${api.url:http://localhost:8080/reader-api-pagination/api/mock/users}")
    private String apiUrl;

    @Value("${api.page.size:10}")
    private int pageSize;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PaginatedApiItemReader itemReader(RestTemplate restTemplate) {
        return new PaginatedApiItemReader(restTemplate, apiUrl, pageSize);
    }

    /**
     * Writer simples que apenas imprime os dados no console
     */
    @Bean
    public ItemWriter<UserData> itemWriter() {
        return items -> {
            System.out.println("\n========================================");
            System.out.println("📦 PROCESSANDO LOTE DE " + items.size() + " ITENS");
            System.out.println("========================================");

            for (UserData item : items) {
                System.out.println("✅ ID: " + item.getId() +
                        " | Nome: " + item.getName() +
                        " | Email: " + item.getEmail() +
                        " | Status: " + item.getStatus());
            }

            System.out.println("========================================\n");
        };
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          PaginatedApiItemReader reader,
                          ItemWriter<UserData> writer) {
        return new StepBuilder("chunkStep", jobRepository)
                .<UserData, UserData>chunk(10, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step chunkStep) {
        return new JobBuilder("paginatedApiJob", jobRepository)
                .start(chunkStep)
                .build();
    }

}