package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements UserMapper {

  @Override
  public int dummyBulkInsert(List<User> user) {
    // 実際のインサート処理を記述する
    return user.size();
  }
}
