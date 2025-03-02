package com.example.demo.chunk;

import lombok.Getter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;

@Getter
public class CustomFlatFileItemReader<User> extends FlatFileItemReader<User> {
  private String fileName;

  @Override
  public void open(ExecutionContext executionContext) {
    super.open(executionContext);
    if (this.getCurrentResource() != null) {
      fileName = this.getCurrentResource().getFilename();
      System.out.println("現在処理中のファイル: " + fileName);
    }
  }

  public Resource getCurrentResource() {
    try {
      Field resourceField = FlatFileItemReader.class.getDeclaredField("resource");
      resourceField.setAccessible(true);
      return (Resource) resourceField.get(this);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Failed to access resource field", e);
    }
  }
}
