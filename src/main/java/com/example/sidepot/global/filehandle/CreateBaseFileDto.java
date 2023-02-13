package com.example.sidepot.global.filehandle;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBaseFileDto {

    private String fileSavePath;
    private String fileOriginName;

    @Builder
    public CreateBaseFileDto(String fileSavePath, String fileOriginName) {
        this.fileSavePath = fileSavePath;
        this.fileOriginName = fileOriginName;
    }


}
