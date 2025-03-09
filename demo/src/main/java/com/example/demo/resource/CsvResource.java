package com.example.demo.resource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CsvResource {
  public Resource[] getResources(String filePath) throws IOException {
    return new PathMatchingResourcePatternResolver().getResources(filePath);
  }
}
