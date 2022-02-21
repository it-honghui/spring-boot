package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author honghui 2022/2/21
 */
public interface FileService {

  String uploadFile(MultipartFile file, String folder) throws IOException;
}
