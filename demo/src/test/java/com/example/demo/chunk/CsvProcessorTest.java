package com.example.demo.chunk;

import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@Slf4j
@DisplayName("CsvProcessorTest")
public class CsvProcessorTest {

  @InjectMocks
  private CsvProcessor csvProcessor;


  @BeforeAll
  public static void beginTest() {
    log.info("CsvProcessorTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("CsvProcessorTest 終了");
  }

  @Test
  @DisplayName("executeTest_noException")
  public void executeTest_noException() throws Exception {
    // SETUP
    log.info("executeTest_noException 開始");
    final User item = createUser(1L);
    final User expected = createUser(1L);
    expected.setName(expected.getName() + "さん");

    // WHEN
    final User actual = csvProcessor.process(item);

    // THEN
    assertEquals(expected, actual);

    log.info("executeTest_noException 終了");

  }

  @Test
  @DisplayName("executeTest_exception")
  public void executeTest_exceptionCheck() throws Exception {
    // SETUP
    log.info("executeTest_exception 開始");

    // WHEN
    final User actual = csvProcessor.process(null);

    // THEN
    assertNull(actual);

    log.info("executeTest_exception 終了");

  }

  private User createUser(final long id) {
    final User user = new User();
    user.setId(id);
    user.setName("name" + id);
    user.setFileName("file" + id);
    return user;
  }
}