package com.example.sidepot.global.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface FileService {
    String BASE_DIR_PATH = new File("").getAbsolutePath() + File.separator + File.separator;

    String getSaveFileName(MultipartFile multipartFile);
    void createBaseSaveDir() throws Exception;

    default BaseFilePath saveFileAndGetFileDto(MultipartFile multipartFile) throws IOException {
        String saveFilePath = getSaveFileName(multipartFile);
        try {
            multipartFile.transferTo(new File(saveFilePath));
        } catch (IOException e){
            e.printStackTrace();
        }

        return new BaseFilePath(saveFilePath, multipartFile.getOriginalFilename());
    }
    default void deleteFile(String saveFilePath) {
        new File(saveFilePath).delete();
    }
}
