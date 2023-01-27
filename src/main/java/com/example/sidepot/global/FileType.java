package com.example.sidepot.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    PROFILE("profile"), CONTRACT_PDF("contract_pdf");

    private String saveDir;

}
