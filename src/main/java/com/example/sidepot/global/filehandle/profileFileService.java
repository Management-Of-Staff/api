package com.example.sidepot.global.filehandle;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Qualifier(value = "profileFileService")
@Service
public class profileFileService implements FileHandler{

    private static final String SAVE_DIR = BASE_DIR_PATH + FileType.PROFILE.getSaveDir() + File.separator;
    private static final String JPG_EXTENSION = ".jpg";
    private static final String PNG_EXTENSION = ".png";


    public profileFileService() {
        new File(SAVE_DIR).mkdirs();
    }


    @Override
    public void createBaseSaveDir() throws Exception {
        new File(SAVE_DIR).mkdirs();
    }

    @Override
    public String getSaveFileName(MultipartFile multipartFile) {
        return SAVE_DIR + UUID.randomUUID() + "_"
                + multipartFile.getOriginalFilename() + getFileExtension(multipartFile.getContentType());
    }

    public String getFileExtension(String fileContentType) {
        if(!fileContentType.contains(MediaType.IMAGE_JPEG_VALUE) && !fileContentType.contains(MediaType.IMAGE_PNG_VALUE))
            throw new Exception(ErrorCode.NOT_SUPPORTED_IMAGE_EXTENSION);
        return fileContentType == MediaType.IMAGE_PNG_VALUE ? PNG_EXTENSION : JPG_EXTENSION;
    }
}
