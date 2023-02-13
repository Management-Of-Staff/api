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
@Service
@Qualifier(value = "contractFileService")
public class contractFileService implements FileHandler{
    private static final String SAVE_DIR = BASE_DIR_PATH + FileType.CONTRACT_PDF.getSaveDir() + File.separator;
    private static final String PDF_EXTENSION = ".pdf";

    public contractFileService() {
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
        if(!fileContentType.contains(MediaType.APPLICATION_PDF_VALUE))
            throw new Exception(ErrorCode.NOT_SUPPORTED_PDF_EXTENSION);
        return PDF_EXTENSION;
    }
}
