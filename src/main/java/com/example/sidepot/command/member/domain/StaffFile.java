package com.example.sidepot.command.member.domain;

import com.example.sidepot.global.file.FileType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "staff_file")
@Entity
public class StaffFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "file_save_path", nullable = false)
    private String fileSavePath;

    @Column(name = "file_origin_name", nullable = false)
    private String fileOriginName;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private StaffFile(Staff staff, String fileSavePath, String fileOriginName, FileType fileType) {
        this.staff = staff;
        this.fileSavePath = fileSavePath;
        this.fileOriginName = fileOriginName;
        this.fileType = fileType;
    }

    public static StaffFile from(Staff staff, String fileSavePath, String fileOriginName, FileType fileType){
        return new StaffFile(staff, fileSavePath, fileOriginName, fileType);
    }
}