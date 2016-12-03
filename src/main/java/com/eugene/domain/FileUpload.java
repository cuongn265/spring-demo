package com.eugene.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Eugene on 12/2/2016.
 */
public class FileUpload {
  MultipartFile multipartFile;

  public FileUpload(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public void setMultipartFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }
}
