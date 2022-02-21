package com.example.demo.controller;

import com.example.demo.config.annotation.PasswordPermission;
import com.example.demo.domain.R;
import com.example.demo.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author honghui 2022/2/21
 */
@RestController
@RequestMapping("/file")
public class FileController {

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PasswordPermission(requirePassword = true)
  @PostMapping("/uploadFile/{password}")
  public R<String> uploadFile(@RequestParam MultipartFile file,
                              @RequestParam String folder) throws IOException {
    return R.ok(fileService.uploadFile(file, folder));
  }

}
