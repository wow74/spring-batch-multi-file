package com.example.demo;

import com.example.demo.chunk.CsvProcessor;
import com.example.demo.chunk.CsvReader;
import com.example.demo.chunk.CsvWriter;
import com.example.demo.repository.UserRepository;
import com.example.demo.resource.CsvResource;
import com.example.demo.tasklet.CsvExistsCheckTasklet1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBatchTest
// DI対象のクラスを列挙する
@ContextConfiguration(classes = {BatchConfig.class, CsvExistsCheckTasklet1.class, CsvReader.class, CsvProcessor.class, CsvWriter.class, CsvResource.class, UserRepository.class})
@EnableAutoConfiguration // 必要そうなbeanを自動で構成してくれる
@Slf4j
@DisplayName("BatchConfigTest")
public class BatchConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @BeforeAll
  public static void beginTest() {
    log.info("BatchConfigTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("BatchConfigTest 終了");
  }

  @Test
  @DisplayName("fileCheckStepTest")
  public void fileCheckStepTest() {
    // SETUP
    log.info("executeTest_existsFile 開始");

    // WHEN
    // 読み込まれたクラスのプロパティファイル読み込みはtest側が使用される
    final JobExecution jobExecution = jobLauncherTestUtils.launchStep("fileCheckStep");

    // THEN
    assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    log.info("executeTest_existsFile 終了");

  }

  @Test
  @DisplayName("demoStepTest")
  public void demoStepTest() {
    // SETUP
    log.info("demoStepTest 開始");

    // WHEN
    final JobExecution jobExecution = jobLauncherTestUtils.launchStep("demoStep");

    // THEN
    assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    log.info("demoStepTest 終了");
  }

  @Test
  @DisplayName("demoJobTest")
  public void demoJobTest() throws Exception {
    // SETUP
    log.info("demoJobTest 開始");

    // WHEN
    final JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    // THEN
    assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    log.info("demoJobTest 終了");
  }


}