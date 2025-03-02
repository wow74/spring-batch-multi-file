package com.example.demo.chunk;

import com.example.demo.model.User;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.Objects;

public class CustomFlatFileItemReader<T extends User> extends FlatFileItemReader<User> {
  private String fileName;

  @Override
  public void open(final ExecutionContext executionContext) {
    super.open(executionContext);
    fileName = Objects.nonNull(getCurrentResource()) ? getCurrentResource().getFilename() : null;
  }

  @Override
  public User read() throws Exception {
    final User item = this.doRead();
    if (Objects.isNull(item)) return null;
    item.setFileName(fileName); // ファイル読み込み値以外をセット
    return item;
  }

  private Resource getCurrentResource() {
    try {
      final Field resourceField = FlatFileItemReader.class.getDeclaredField("resource");

      // privateフィールドにアクセスする
      // VMでリフレクションの使用が禁止されている場合、SecurityExceptionを投げる
      resourceField.setAccessible(true);
      return (Resource) resourceField.get(this);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("リソースへのアクセスに失敗", e);
    }
  }

}
