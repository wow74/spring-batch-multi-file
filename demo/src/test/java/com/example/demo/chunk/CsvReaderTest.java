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
import org.springframework.batch.item.file.MultiResourceItemReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@Slf4j
@DisplayName("CsvReaderTest")
public class CsvReaderTest {

  @InjectMocks
  private CsvReader csvReader;

  @BeforeAll
  public static void beginTest() {
    log.info("CsvReaderTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("CsvReaderTest 終了");
  }

  @Test
  @DisplayName("readTest")
  public void readTest() throws Exception {
    // SETUP
    log.info("readTest 開始");
    final User[] expectedArray = {
            createUser(2L, "hanako", "female.csv"),
            createUser(3L, "hana", "female.csv"),
            createUser(1L, "taro", "male.csv")
    };

    // WHEN
    final MultiResourceItemReader<User> reader = csvReader.read("classpath:test/csv/*.csv");

    assertEquals(expectedArray[0], reader.read());
    assertEquals(expectedArray[1], reader.read());
    assertEquals(expectedArray[2], reader.read());
    assertNull(reader.read());

    // THEN
    log.info("readTest 終了");
  }

  private User createUser(final long id, final String name, final String fileName) {
    final User user = new User();
    user.setId(id);
    user.setName(name);
    user.setFileName(fileName);
    return user;
  }

}