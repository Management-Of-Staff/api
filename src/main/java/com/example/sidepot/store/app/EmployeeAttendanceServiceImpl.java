package com.example.sidepot.store.app;

import com.example.sidepot.store.domain.EmployeeAttendance;
import com.example.sidepot.store.domain.EmployeeAttendanceRepository;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.store.dto.AttendanceRequestDto;
import com.example.sidepot.store.dto.AttendanceResponseDto;
import com.example.sidepot.store.dto.EmployeeAttendanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService{
    private final EmployeeAttendanceRepository employeeAttendanceRepository;
    private final StoreRepository storeRepository;
    @Override
    public AttendanceResponseDto checkAttendance(Long storeId, AttendanceRequestDto attendanceRequestDto) {
        return null;
    }

    @Override
    public List<EmployeeAttendanceDto> getCurrentAttendance(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("No matching store ID found."));

        // 수정 예정
        LocalDate 매장시작시간 = LocalDate.now();
        LocalDate 매장종료시간 = LocalDate.now();

        List<EmployeeAttendance> employeeAttendances = employeeAttendanceRepository.getTodayNormalAttendanceListByStore(store, 매장시작시간, 매장종료시간);
        return employeeAttendances.stream()
                .map(EmployeeAttendanceDto::from)
                .collect(Collectors.toList());
    }
}
