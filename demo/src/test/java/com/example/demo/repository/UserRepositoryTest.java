package com.example.demo.repository;

import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

// @SpringBatchTest // SpringBatchの機能(Tasklet, Chunkなど)をテストするときに使うアノテーション
@SpringBootTest // RepositoryはBatch機能ではないためSpringBootTestアノテーションを使用
@Slf4j
@DisplayName("UserRepositoryTest")
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @BeforeAll
  public static void beginTest() {
    log.info("UserRepositoryTest 開始");
  }

  @AfterAll
  public static void endTest() {
    log.info("UserRepositoryTest 終了");
  }

  @Test
  @Transactional
  @DisplayName("dummyBulkInsertTest")
  public void dummyBulkInsertTest() {
    // SETUP
    log.info("dummyBulkInsertTest 開始");
    final List<User> expected = List.of(createUserData(1L), createUserData(2L));

    // WHEN
    userRepository.dummyBulkInsert(expected);

    // THEN
    // List<User> actual = xxx.selectByExample();
    final List<User> actual = expected; // selectでデータを取得したとする

    assertIterableEquals(expected, actual);

    // CLEANUP
    // @Transactionalでロールバック実行
    log.info("dummyBulkInsertTest 終了");

  }

  private User createUserData(final long id) {
    final User user = new User();
    user.setId(id);
    user.setName("name" + id);
    user.setFileName("fileName" + id);
    return user;
  }

}