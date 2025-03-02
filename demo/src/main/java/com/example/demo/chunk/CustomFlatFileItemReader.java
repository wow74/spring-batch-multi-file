package com.example.demo.chunk;

import com.example.demo.model.User;
import lombok.Getter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
public class CustomFlatFileItemReader<T extends User> extends FlatFileItemReader<User> {
  private String fileName;

  @Override
  public void open(ExecutionContext executionContext) {
    super.open(executionContext);
    if (this.getCurrentResource() != null) {
      fileName = this.getCurrentResource().getFilename();
//      System.out.println("現在処理中のファイル: " + fileName);
    }
  }

  @Override
  @Nullable
  public User read() throws Exception {
     User item = this.doRead();
    if (Objects.isNull(item)) {
      return null;
    }
    System.out.println(getCurrentResource().getFilename());
//    ((User) item).getId();
    item.setFileName(fileName);
    return item;
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
