package com.example.sidepot.attendance.app;

import static java.util.stream.Collectors.*;

import com.example.sidepot.attendance.domain.Attendance;
import com.example.sidepot.attendance.domain.AttendanceRepository;
import com.example.sidepot.attendance.dto.AttendanceRequestDto;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;

    @Override
    @Transactional
    public AttendanceResponseDto createAttendanceForCheckIn(Long storeId, Long employeeId) {
        Employment employment = findEmploymentById(storeId);
        Store store = findStoreById(storeId);
        Attendance attendance = attendanceRepository.save(Attendance.ofCheckIn(store, employment));
        return AttendanceResponseDto.from(attendance);
    }

    @Override
    @Transactional
    public void createAttendanceForCheckOut(Long attendanceId) {
        Attendance attendance = findAttendanceById(attendanceId);
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
        return attendanceRepository.findById(attendanceId)
            .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_ATTENDANCE));
    }

}
