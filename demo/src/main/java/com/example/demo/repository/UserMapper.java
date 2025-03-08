package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.List;

//@Mapper
public interface UserMapper {

  public int dummyBulkInsert(List<User> user);
}
