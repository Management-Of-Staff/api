package com.example.sidepot.attendance.app;

import com.example.sidepot.attendance.domain.Attendance;
import com.example.sidepot.attendance.domain.AttendanceRepository;
import com.example.sidepot.attendance.domain.AttendanceStatus;
import com.example.sidepot.attendance.dto.AttendanceResponseDto;
import com.example.sidepot.attendance.dto.EmployeeAttendanceDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.domain.Employment;
import com.example.sidepot.work.domain.EmploymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 출석 관련 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;

    /**
     * 출근 체크
     * 매장 ID와 직원 ID 값을 사용하여 출근 등록
     *
     * @param storeId 매장 ID
     * @param employeeId 직원 ID
     * @return
     */
    @Override
    @Transactional
    public AttendanceResponseDto createAttendanceForCheckIn(Long storeId, Long employeeId) {
        Employment employment = findEmploymentById(storeId);
        Store store = findStoreById(storeId);

        validateCheckIn(store,employment);

        Attendance attendance = attendanceRepository.save(Attendance.ofCheckIn(store, employment));
        return AttendanceResponseDto.from(attendance);
    }

    /**
     * 퇴근 체크
     * 출석 ID를 사용하여 퇴근 등록
     *
     * @param attendanceId
     */
    @Override
    @Transactional
    public void updateAttendanceForCheckOut(Long attendanceId) {
        Attendance attendance = findAttendanceById(attendanceId);
        validateCheckOut(attendance);
        attendance.setCheckOutTime();
    }

    @Override
    public List<EmployeeAttendanceDto> getCurrentAttendanceList(Long storeId) {
        Store store = findStoreById(storeId);
        List<Attendance> attendances = attendanceRepository.getCheckInListByStore(store);
        return attendances.stream()
                .map(EmployeeAttendanceDto::from)
                .collect(toList());
    }

    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STORE));
    }

    private Employment findEmploymentById(Long storeId) {
        return employmentRepository.findById(storeId)
            .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
    }

    private Attendance findAttendanceById(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_ATTENDANCE));
        return attendance;
    }

    private void validateCheckIn(Store store, Employment employment) {
        if(attendanceRepository.findAttendanceByCondition(AttendanceStatus.CHECK_IN,store, employment)
                .isPresent()) {
            log.error("이미 출근 체크된 직원입니다. 매장 ID: {}, 직원 ID: {}", store.getStoreId(), employment.getEmploymentId());
            throw new Exception(ErrorCode.ALREADY_CHECKED_IN);
        }
    }

    private void validateCheckOut(Attendance attendance) {
        if(attendance.isCheckOut()){
            log.error("이미 퇴근 체크된 직원입니다. 출석 ID: {}", attendance.getId());
            throw new Exception(ErrorCode.ALREADY_CHECKED_OUT);
        }
    }

}
