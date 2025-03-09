package com.example.demo.chunk;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@StepScope
public class CsvWriter implements ItemWriter<User> {

  @Autowired
  private UserRepository userRepository;

  @Override
  public void write(final Chunk<? extends User> items) throws Exception {
    System.out.println("write");
    userRepository.dummyBulkInsert(new ArrayList<>(items.getItems()));
    System.out.println("登録完了");
  }
}
