package com.example.demo.chunk;

import com.example.demo.model.User;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
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
    return new PathMatchingResourcePatternResolver().getResources("classpath:csv/*.csv");
  }

  @StepScope
  public MultiResourceItemReader<User> read() throws IOException {
    final MultiResourceItemReader<User> multiReader = new MultiResourceItemReader<>();
    multiReader.setResources(getCsvFilesFromResources());
    multiReader.setDelegate(singleFileReader());

    return multiReader;
  }

  private FlatFileItemReader<User> singleFileReader() {
    CustomFlatFileItemReader<User> reader = new CustomFlatFileItemReader<>();
    reader.setName("csvReader");
    reader.setLinesToSkip(1);
    reader.setEncoding(StandardCharsets.UTF_8.name());
    reader.setLineMapper(new DefaultLineMapper<User>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames("id", "name");
      }});
      setFieldSetMapper(new CustomFieldSetMapper());
    }});
    return reader;
  }

  private static class CustomFieldSetMapper implements FieldSetMapper<User> {
    @Override
    public User mapFieldSet(final FieldSet fieldSet) {
      final User user = new User();
      user.setId(fieldSet.readLong("id"));
      user.setName(fieldSet.readString("name"));
      user.setFileName(null); // ファイル名をセット
      return user;
    }

  }
}
