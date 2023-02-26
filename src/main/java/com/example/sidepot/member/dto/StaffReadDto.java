package com.example.sidepot.member.dto;
import com.example.sidepot.work.domain.WeekWorkTime;
import lombok.Data;

import java.util.List;


@Data
public class StaffReadDto {
    private String name;
    private String workingStatus;
    private List<WeekWorkTime> weekWorkTimeList;
    private String profilePath;
    private Boolean healthCertificateCheck;
}
