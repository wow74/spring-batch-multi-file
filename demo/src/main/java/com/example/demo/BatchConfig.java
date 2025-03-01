package com.example.demo;

import com.example.demo.chunk.CsvProcessor;
import com.example.demo.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.charset.StandardCharsets;

@Configuration
@PropertySource("classpath:property/demo.properties")
public class BatchConfig {

//  @Autowired
//  private CsvReader reader;

  @Autowired
  private CsvProcessor processor;

  @Value("${csv.path}")
  private String csvPath;

  @Bean
  @StepScope
  public FlatFileItemReader<User> read() {
    String[] nameArray = new String[]{"id", "name"};

    return new FlatFileItemReaderBuilder<User>()
            .name("csvReader")
            .resource(new ClassPathResource(csvPath))
            .linesToSkip(1)
            .encoding(StandardCharsets.UTF_8.name())
            .delimited()
            .names(nameArray) // カラム名
            .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
              {
                setTargetType(User.class); // データを指定のクラスにバインド
              }
            })
            .build();

  }

  @Bean
  public Step demoStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
    return new StepBuilder("demoStep", jobRepository)
            .<User, User>chunk(10, transactionManager)
            .reader(read())
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
