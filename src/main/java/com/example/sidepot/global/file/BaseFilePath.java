package com.example.sidepot.global.file;

import lombok.Getter;

@Getter
public class BaseFilePath {

    private String fileSavePath;
    private String fileOriginName;


    public BaseFilePath(String fileSavePath, String fileOriginName) {
        this.fileSavePath = fileSavePath;
        this.fileOriginName = fileOriginName;
    }


}
