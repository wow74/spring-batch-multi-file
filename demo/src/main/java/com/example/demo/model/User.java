package com.example.demo.model;

import lombok.Data;
import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

@Data
public class User implements ResourceAware {
  private long id;
  private String name;
  private String fileName;

  @Override
  public void setResource(final Resource resource) {
    fileName = resource.getFilename();
  }
}
