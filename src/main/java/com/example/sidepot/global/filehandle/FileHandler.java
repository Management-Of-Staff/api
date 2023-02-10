package com.example.sidepot.global.filehandle;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface FileHandler {
    String BASE_DIR_PATH = new File("").getAbsolutePath() + File.separator + File.separator;

    String getSaveFileName(MultipartFile multipartFile);
    void createBaseSaveDir() throws Exception;

    default List<CreateBaseFileDto> saveFileAndGetFileDto(MultipartFile multipartFile) throws IOException {
        String saveFileName = getSaveFileName(multipartFile);
        try {
            multipartFile.transferTo(new File(saveFileName));
        } catch (IOException e){
            e.printStackTrace();
        }

        return Collections.singletonList(CreateBaseFileDto.builder()
                .fileOriginName(multipartFile.getOriginalFilename())
                .fileSavePath(saveFileName)
                .build());
    }
    default void deleteFile(String saveFilePath) {
        new File(saveFilePath).delete();
    }
}
