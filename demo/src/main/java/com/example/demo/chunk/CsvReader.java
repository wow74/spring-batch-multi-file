package com.example.demo.chunk;

import com.example.demo.model.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CsvReader {

  @Value("${csv.path}")
  private String csvPath;

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
}
