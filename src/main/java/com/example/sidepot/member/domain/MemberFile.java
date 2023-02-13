package com.example.sidepot.member.domain;

import com.example.sidepot.global.filehandle.FileType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MemberFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    @Column(name = "file_save_name", nullable = false)
    private String fileSaveName;

    @Column(name = "file_origin_name", nullable = false)
    private String fileOriginName;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Builder
    public MemberFile(Auth auth, String fileSaveName, String fileOriginName, FileType fileType) {
        this.auth = auth;
        this.fileSaveName = fileSaveName;
        this.fileOriginName = fileOriginName;
        this.fileType = fileType;
    }
}