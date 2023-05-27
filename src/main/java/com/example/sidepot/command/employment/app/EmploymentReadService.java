package com.example.sidepot.command.employment.app;

import com.example.sidepot.command.attendance.domain.Attendance;
import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.command.attendance.domain.CoverAttendance;
import com.example.sidepot.command.attendance.repository.AttendanceRepository;
import com.example.sidepot.command.attendance.repository.CoverAttendanceRepository;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.WorkManager;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import com.example.sidepot.command.work.repository.WorkManagerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmploymentReadService {
    private final WorkManagerRepository workManagerRepository;
    private final AttendanceRepository attendanceRepository;
    private final CoverWorkRepository coverWorkRepository;
    private final CoverAttendanceRepository coverAttendanceRepository;

    /**
     * 테스트셋이 검증 필요해서 리팩토링 안하고 원본 그대로 내비둠
     */
    @Transactional(readOnly = true)
    public List<EmploymentListResDto> 직원목록조회(Long storeId, LocalDate onDay) {
        List<WorkManager> workManagerListPs = workManagerRepository.findAllByStoreInfo_StoreId(storeId);
        List<EmploymentListResDto> employmentListResDto = new ArrayList<>();
        for(WorkManager workManager : workManagerListPs){
            EmploymentListResDto dto = new EmploymentListResDto(workManager);
            List<DayOfWeek> days = workManager.getWorkTimeList().stream().map(wm -> wm.getDayOfWeek()).collect(Collectors.toList());
            AttendanceStatus attendanceStatus = AttendanceStatus.REST;
            for(WorkTime workTime : workManager.getWorkTimeList()){
                Optional<Attendance> attendanceOp = attendanceRepository.findByWorkTimeIdAndWorkDateTime_DayOfWeek(workTime.getWorkTimeId(), onDay.getDayOfWeek());
                if(attendanceOp.isPresent()){
                    attendanceStatus = attendanceOp.get().getAttendanceStatus();
                    break;
                }
                Optional<CoverWork> coverWorkOp = coverWorkRepository.findByWorkTime_WorkTimeIdAndCoverDateTime_CoverDate(workTime.getWorkTimeId(), onDay);
                if(coverWorkOp.isPresent()){
                    Optional<CoverAttendance> coverAttendancePs = coverAttendanceRepository.findByCoverWorkIdAndWorkDateTime_WorkDate(coverWorkOp.get().getCoverWorkId(), onDay);
                    CoverAttendance coverAttendance = coverAttendancePs.orElseThrow();
                    attendanceStatus = coverAttendance.getAttendanceStatus();
                    break;
                }
            }
            employmentListResDto.add(dto.setAttendanceStatus(attendanceStatus).setDayOfWeekList(days));
        }
        return employmentListResDto;
    }
    @Getter
    public static class EmploymentListResDto{
        private Long staffId;
        private String staffName;
        private String profileImagePath;
        private List<DayOfWeek> dayOfWeekList;
        private AttendanceStatus attendanceStatus;

        public EmploymentListResDto(WorkManager workManager) {
            this.staffId = workManager.getWorkerId().getWorkerId();
            this.staffName = workManager.getWorkerId().getWorkerName();
            this.profileImagePath = null;
        }

        public EmploymentListResDto setAttendanceStatus(AttendanceStatus attendanceStatus) {
            this.attendanceStatus = attendanceStatus;
            return this;
        }

        public EmploymentListResDto setDayOfWeekList(List<DayOfWeek> days){
            this.dayOfWeekList = days;
            return this;
        }
    }
}
