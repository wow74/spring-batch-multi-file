package com.example.demo.resource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(MockitoExtension.class)
@Slf4j
@DisplayName("CsvReaderTest")
public class CsvResourceTest {

  @InjectMocks
  private CsvResource csvResource;

  @BeforeAll
  public static void beginTest() {
    log.info("CsvResourceTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("CsvResourceTest 終了");
  }

  @Test
  @DisplayName("getResourcesTest")
  public void getResourcesTest() throws IOException {
    // SETUP
    final String filePath = "classpath:test/csv/*.csv";
    final String[] expected = {"female.csv", "male.csv"};

    // WHEN
    final Resource[] resources = csvResource.getResources(filePath);
    final String[] actual = Arrays.stream(resources).map(Resource::getFilename).toArray(String[]::new);

    // THEN
    assertArrayEquals(expected, actual);
    log.info("getResourcesTest 開始");
  }
}
