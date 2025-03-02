package com.example.demo;

import com.example.demo.chunk.CsvProcessor;
import com.example.demo.chunk.CsvReader;
import com.example.demo.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

  @Autowired
  private CsvReader reader;

  @Autowired
  private CsvProcessor processor;

  private Step demoStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
    return new StepBuilder("demoStep", jobRepository)
            .<User, User>chunk(10, transactionManager)
            .reader(reader.read())
            .processor(processor)
            .writer(items -> {})
            .build();
  }

  @Bean
  public Job demoJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) throws Exception {
    return new JobBuilder("demoJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(demoStep(jobRepository, transactionManager))
            .build();
  }
}
