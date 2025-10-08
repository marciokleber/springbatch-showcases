package com.github.marciokleber.springbatchsimpletask.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("job",jobRepository)
                .start(step(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("""
                        
                        ███████╗██╗███╗   ███╗██████╗ ██╗     ███████╗    ████████╗ █████╗ ███████╗██╗  ██╗
                        ██╔════╝██║████╗ ████║██╔══██╗██║     ██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝
                        ███████╗██║██╔████╔██║██████╔╝██║     █████╗         ██║   ███████║███████╗█████╔╝\s
                        ╚════██║██║██║╚██╔╝██║██╔═══╝ ██║     ██╔══╝         ██║   ██╔══██║╚════██║██╔═██╗\s
                        ███████║██║██║ ╚═╝ ██║██║     ███████╗███████╗       ██║   ██║  ██║███████║██║  ██╗
                        ╚══════╝╚═╝╚═╝     ╚═╝╚═╝     ╚══════╝╚══════╝       ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
                        """);
                return RepeatStatus.FINISHED;
            }
        },transactionManager ).build();
    }


}
