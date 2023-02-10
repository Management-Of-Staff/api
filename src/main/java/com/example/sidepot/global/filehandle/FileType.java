package com.example.sidepot.global.filehandle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    PROFILE("profile"), CONTRACT_PDF("contract");

    private String saveDir;

}
