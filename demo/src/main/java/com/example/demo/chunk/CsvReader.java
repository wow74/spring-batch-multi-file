package com.example.demo.chunk;

import com.example.demo.model.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@PropertySource("classpath:property/demo.properties")
public class CsvReader {

  public Resource[] getCsvFilesFromResources() throws IOException {
    return new PathMatchingResourcePatternResolver().getResources("classpath:csv/*.csv"); // "csv" フォルダ内のCSVを取得
  }

  @StepScope
  public MultiResourceItemReader<User> read() throws IOException {
    final MultiResourceItemReader<User> multiReader = new MultiResourceItemReader<>();
    multiReader.setResources(getCsvFilesFromResources());
    multiReader.setDelegate(singleFileReader());

    return multiReader;
  }

  private FlatFileItemReader<User> singleFileReader() {
    final String[] nameArray = new String[]{"id", "name"};
    return new FlatFileItemReaderBuilder<User>()
            .name("csvReader")
            .linesToSkip(1)
            .encoding(StandardCharsets.UTF_8.name())
            .delimited()
            .names(nameArray)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
              setTargetType(User.class);
            }})
            .build();
  }
}
