package com.example.demo.tasklet;

import com.example.demo.resource.CsvResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// @ExtendWith(クラス)でテストに使用するクラスを指定できる
// MockitoExtensionを指定するとMockitoテストが可能になる(モックが使える)
// DIしたくないときに使える
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Slf4j
@DisplayName("CsvExistsCheckTaskletTest")
public class CsvExistsCheckTaskletTest1 {

  @InjectMocks // モックを使いたいクラスに付与することで@MockをDIしてくれる
  private CsvExistsCheckTasklet1 csvExistsCheckTasklet;

  @Mock // テスト元でDIしているものをモック化する
  private CsvResource csvResource;

  @BeforeAll
  public static void beginTest() {
    log.info("CsvExistsCheckTaskletTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("CsvExistsCheckTaskletTest 終了");
  }

  @Test
  @DisplayName("executeTest_existsFile")
  public void executeTest_success() throws IOException {
    // SETUP
    log.info("executeTest_existsFile 開始");
    ReflectionTestUtils.setField(csvExistsCheckTasklet, "filePath", "", String.class);
    final RepeatStatus expected = RepeatStatus.FINISHED;

    // WHEN
    when(csvResource.getResources(anyString())).thenReturn(new Resource[]{any()});
    final RepeatStatus actual = csvExistsCheckTasklet.execute(null, null);

    // THEN
    assertEquals(expected, actual);

    log.info("executeTest_existsFile 終了");

  }

  @Test
  @DisplayName("executeTest_notExistsFile")
  public void executeTest_fail() throws IOException {
    // SETUP
    log.info("executeTest_notExistsFile 開始");
    ReflectionTestUtils.setField(csvExistsCheckTasklet, "filePath", "", String.class);

    // THEN
    when(csvResource.getResources(anyString())).thenReturn(new Resource[]{});
    assertThrows(FileNotFoundException.class, () -> csvExistsCheckTasklet.execute(null, null));
    log.info("executeTest_notExistsFile 終了");
  }
}
