package com.example.demo.chunk;

import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class CsvProcessor implements ItemProcessor<User, User> {

  @Override
  public User process(final User item) throws Exception {
    try {
      System.out.println("demo");
      System.out.println(item.toString());
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
      return null;
    }
    return item;
  }
}
