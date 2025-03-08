package com.example.demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
@StepScope
@PropertySource("classpath:property/demo.properties")
public class CsvExistsCheckTasklet implements Tasklet {

  @Value("${csv.file.path}")
  private String filePath;

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext context) throws IOException {
    final Resource[] resources = new PathMatchingResourcePatternResolver().getResources(filePath);
    if (resources.length == 0) throw new FileNotFoundException("csvファイルが見つかりませんでした");
    return RepeatStatus.FINISHED;
  }
}
