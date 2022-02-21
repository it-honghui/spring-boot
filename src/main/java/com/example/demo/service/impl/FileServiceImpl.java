package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.demo.domain.ERR;
import com.example.demo.domain.Recode;
import com.example.demo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author honghui 2022/2/21
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

  @Value("${file.url}")
  private String fileUrl;
  @Value("${file.path}")
  private String filePath;
  @Value("${file.allowedFileType}")
  private String allowedFileType;
  @Value("${file.maxSize}")
  private int maxSize;
  @Value("${file.maxFileNameLength}")
  private int maxFileNameLength;

  @Override
  public String uploadFile(MultipartFile multipartFile, String folder) throws IOException {
    String originalFilename = multipartFile.getOriginalFilename();
    if (StrUtil.isEmpty(originalFilename)) {
      throw ERR.of(Recode.UPLOAD_FILE_CANNOT_BE_EMPTY);
    }
    if (allowedFileType != null && allowedFileType.length() > 0
        && !Arrays.asList(allowedFileType.split(",")).contains(getExtensionName(originalFilename))) {
      throw ERR.of(Recode.FILE_UPLOAD_TYPE_NOT_ALLOWED);
    }
    if (multipartFile.getSize() > maxSize * 1024 * 1024L) {
      throw ERR.of(Recode.FILE_TOO_LARGE);
    }
    if (originalFilename.length() > maxFileNameLength) {
      throw ERR.of(Recode.FILE_NAME_TOO_LONG);
    }
    File file = getAbsoluteFile(filePath + folder, originalFilename);
    multipartFile.transferTo(file);
    return fileUrl + folder + "/" + originalFilename;
  }

  private String getExtensionName(String filename) {
    if ((filename != null) && (filename.length() > 0)) {
      int dot = filename.lastIndexOf('.');
      if ((dot > -1) && (dot < (filename.length() - 1))) {
        return filename.substring(dot + 1);
      }
    }
    return filename;
  }

  private File getAbsoluteFile(String uploadDir, String filename) throws IOException {
    File file = new File(uploadDir + File.separator + "/" + filename);
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdirs();
    }
    if (!file.exists()) {
      file.createNewFile();
    }
    return file;
  }

}
