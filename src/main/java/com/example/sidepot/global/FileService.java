package com.example.sidepot.global;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileService {

    private final static String ABSOLUTE_PATH = new File("").getAbsolutePath() + File.separator + File.separator;

    public String parseProfileImage(MultipartFile multipartFile, FileType fileType) throws Exception, IOException {
        String saveFilePath = "";
        if(multipartFile !=null){
            File file = crateSaveDir(fileType.getSaveDir());
            saveFilePath = file + File.separator + getFileName(multipartFile);

            try {
                multipartFile.transferTo(new File(saveFilePath));
            } catch (IOException e){
                throw e;
            }
        }
        return saveFilePath;

    }

    private File crateSaveDir(String fileDir) throws IOException {
        File file = new File(ABSOLUTE_PATH + fileDir);
        if(!file.exists()) {
            boolean isSuccessful = file.mkdirs();
            if(!isSuccessful) throw new IOException();
        }
        return file;
    }

    private String getFileName(MultipartFile multipartFile) {
        return UUID.randomUUID()
                + multipartFile.getOriginalFilename() + getExtension(multipartFile.getContentType());
    }

    public void deleteProfile(String profileName, FileType fileType){
        File file = new File(ABSOLUTE_PATH + fileType.getSaveDir() + File.separator + profileName);
        file.delete();
    }

    private String getExtension(String contentType) {
        String originalFileExtension;
        if(contentType.contains("image/jpeg"))
            originalFileExtension = ".jpg";
        else if(contentType.contains("image/png"))
            originalFileExtension = ".png";
        else if(contentType.contains("application/pdf"))
            originalFileExtension = ".pdf";
        else {
            throw new Exception(ErrorCode.FAILED_UPLOAD_PROFILE);
        }
        return originalFileExtension;
    }
}
