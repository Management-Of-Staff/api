package com.example.sidepot.command.employment.app;

import com.example.sidepot.command.attendance.domain.Attendance;
import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.command.attendance.domain.CoverAttendance;
import com.example.sidepot.command.attendance.repository.AttendanceDaoRepository;
import com.example.sidepot.command.attendance.repository.AttendanceRepository;
import com.example.sidepot.command.attendance.repository.CoverAttendanceDaoRepository;
import com.example.sidepot.command.attendance.repository.CoverAttendanceRepository;
import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.employment.domain.EmploymentRepository;
import com.example.sidepot.command.employment.dto.EmploymentListResDto;
import com.example.sidepot.command.employment.query.EmploymentDaoRepository;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.WorkManager;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import com.example.sidepot.command.work.repository.WorkManagerRepository;
import com.example.sidepot.command.work.repository.query.CoverWorkDaoRepository;
import com.example.sidepot.command.work.repository.query.WorkTimeDaoRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    private final AttendanceDaoRepository attendanceDaoRepository;
    private final CoverAttendanceDaoRepository coverAttendanceDaoRepository;
    private final CoverWorkDaoRepository coverWorkDaoRepository;
    private final EmploymentDaoRepository employmentDaoRepository;

    /**
     * 직원 목록 조회
     * 도메인이 6개에서 조회하는 코드 -> 어댑터 포기 세미 조회모델 처럼 씀
     */
    @Transactional(readOnly = true)
    public List<EmploymentListResDto> readEmploymentsByStore(Long storeId, LocalDate onDay) {
        List<WorkManager> wmListPs = findWmByStoreID(storeId);
        List<EmploymentListResDto> employmentListResDto = createEmListResDto(wmListPs, onDay);
        return employmentListResDto;
    }

    // #유틸
    private List<EmploymentListResDto> createEmListResDto(List<WorkManager> wmListPs, LocalDate onDay) {
        List<EmploymentListResDto> employmentListResDto = new ArrayList<>();
        for (WorkManager workManager : wmListPs) {
            EmploymentListResDto dto = new EmploymentListResDto(workManager);
            List<DayOfWeek> days = getDayOfWeekList(workManager);
            AttendanceStatus attendanceStatus = getAttendanceStatus(onDay, workManager);
            employmentListResDto.add(dto.setAttendanceStatus(attendanceStatus).setDayOfWeekList(days));
        }
        return employmentListResDto;
    }

    // #도메인 서비스 // 도메인이 서로 1:1 관계일 때 메소드 // 1:N도 가능한지
    private AttendanceStatus getAttendanceStatus(LocalDate onDay, WorkManager workManager) {
        WorkTime onDayWt = getOnDayWt(onDay, workManager);
        AttendanceStatus attendanceStatus = AttendanceStatus.REST;

        if (onDayWt != null) {
            Optional<Attendance> attendanceOp = attendanceDaoRepository.findAttByWtIDAndOnDay(onDayWt.getWorkTimeId(), onDay);
            if (attendanceOp.isPresent()) {
                attendanceStatus = attendanceOp.get().getAttendanceStatus();
            } else {
                attendanceStatus = getAttStatusWithCw(onDay, attendanceStatus, onDayWt);
            }
        }
        return attendanceStatus;
    }

    // #도메인 서비스
    private AttendanceStatus getAttStatusWithCw(LocalDate onDay, AttendanceStatus attendanceStatus, WorkTime onDayWt) {
        Optional<CoverWork> cwOp = coverWorkDaoRepository.findCwByWtIdOnDay(onDayWt.getWorkTimeId(), onDay);
        if(cwOp.isPresent()){
            CoverWork cwPs= cwOp.get();
            Optional<CoverAttendance> cttOp = coverAttendanceDaoRepository.findCttByCwIdOnDay(cwPs.getCoverWorkId(), onDay);
            attendanceStatus =  cttOp.get().getAttendanceStatus();
        }
        return attendanceStatus;
    }

    // #유틸
    private  WorkTime getOnDayWt(LocalDate onDay, WorkManager workManager) {
        WorkTime onDayWt = workManager.getWorkTimeList().stream()
                .filter(workTime -> workTime.getDayOfWeek().equals(onDay.getDayOfWeek()))
                .findFirst()
                .orElse(null);
        return onDayWt;
    }

    // #유틸
    private static List<DayOfWeek> getDayOfWeekList(WorkManager workManager) {
        List<DayOfWeek> days = workManager.getWorkTimeList().stream().map(wm -> wm.getDayOfWeek()).collect(Collectors.toList());
        return days;
    }

    // #유틸
    private List<WorkManager> findWmByStoreID(Long storeId) {
        List<WorkManager> workManagerListPs = workManagerRepository.findAllByStoreInfo_StoreId(storeId);
        return workManagerListPs;
    }
}
