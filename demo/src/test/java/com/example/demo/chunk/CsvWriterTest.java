package com.example.demo.chunk;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.io.ByteArrayOutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
@DisplayName("CsvWriterTest")
public class CsvWriterTest {

  @InjectMocks
  private CsvWriter csvWriter;

  @Mock
  private UserRepository userRepository;

  @BeforeAll
  public static void beginTest() {
    log.info("CsvWriterTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("CsvWriterTest 終了");
  }

  @Test
  @DisplayName("write")
  public void write() throws Exception {
    // SETUP
    log.info("CsvWriterTest 開始");
    final Chunk<User> items = new Chunk<>(new User(), new User());

    // WHEN
    csvWriter.write(items);

    // THEN
    verify(userRepository, times(1)).dummyBulkInsert(any());

    log.info("CsvWriterTest 終了");

  }


}