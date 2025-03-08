package com.example.demo;

import com.example.demo.chunk.CsvProcessor;
import com.example.demo.chunk.CsvReader;
import com.example.demo.chunk.CsvWriter;
import com.example.demo.model.User;
import com.example.demo.tasklet.CsvExistsCheckTasklet;
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

import java.io.IOException;
import java.io.PrintStream;

@Configuration
public class BatchConfig {

  @Autowired
  private CsvExistsCheckTasklet tasklet;

  @Autowired
  private CsvReader reader;

  @Autowired
  private CsvProcessor processor;

  @Autowired
  private CsvWriter writer;

  @Bean
  public Step fileCheckStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
    return new StepBuilder("fileCheckStep", jobRepository)
            .tasklet(tasklet, transactionManager)
            .build();
  }

  @Bean
  public Step demoStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) throws IOException {
    return new StepBuilder("demoStep", jobRepository)
            .<User, User>chunk(10, transactionManager)
            .reader(reader.read())
            .processor(processor)
            .writer(writer)
            .build();
  }

  @Bean
  public Job demoJob(final JobRepository jobRepository, final PlatformTransactionManager transactionManager,
                     final Step fileCheckStep, final Step demoStep) throws Exception {
    System.setOut(new PrintStream(System.out, true, "UTF-8"));
    return new JobBuilder("demoJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(fileCheckStep)
            .next(demoStep)
            .build();
  }
}
