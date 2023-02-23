package com.example.sidepot.member.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.*;
import com.example.sidepot.member.dto.ContractCreateDto.*;
import com.example.sidepot.member.dto.EmploymentReadResponseDto.*;
import com.example.sidepot.member.dto.WeekWorkAddRequest;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.domain.DayWorkTime;
import com.example.sidepot.work.domain.DayWorkTimeRepository;
import com.example.sidepot.work.domain.WeekWorkTime;
import com.example.sidepot.work.domain.WeekWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmploymentService {
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;
    private final AuthRepository authRepository;
    private final OwnerRepository ownerRepository;

    private final WeekWorkTimeRepository weekWorkTimeRepository;


    @Transactional(readOnly = true)
    public ResponseDto readAllStaffByStoreId(Auth auth, Long storeId){
        Store verifiedStore = storeRepository.findByOwner_AuthIdAndStoreId(auth.getAuthId(),storeId).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        List<ReadEmploymentListResponseDto> employmentListResponseDtoList =
                employmentRepository.findAllByStore_StoreId(verifiedStore.getStoreId())
                        .stream()
                        .map(ReadEmploymentListResponseDto::of)
                        .collect(Collectors.toList());
        return ResponseDto.builder()
                .path(String.format("/staff-management/" + storeId))
                .method("GET")
                .statusCode(200)
                .message("매장 직원 목록 조회")
                .data(employmentListResponseDtoList)

                .build();
    }

    @Transactional
    public ResponseDto readEmployment(Auth auth, Long storeId, Long staffId){
        Store verifiedStore = storeRepository.findByOwner_AuthIdAndStoreId(auth.getAuthId(),storeId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        Employment employment = employmentRepository.findByStore_StoreIdAndStaff_AuthId(verifiedStore.getStoreId(), staffId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STAFF_IN_STORE));
        return ResponseDto.builder()
                .path(String.format("/staff-management/store/" + storeId + "/staff/" + staffId))
                .method(HttpMethod.GET.name())
                .statusCode(HttpStatus.OK.value())
                .message("매장 직원 조회")
                .data(ReadEmploymentResponseDto.of(employment))
                .build();
    }

    @Transactional
    public ResponseDto addStaffToStoreByStoreId(Long storeId, Long staffId){
        Store store = storeRepository.findById(storeId).orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        Staff staff = staffRepository.findById(staffId).orElseThrow(()-> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        employmentRepository.save(Employment.of(store, staff, staff.getName()));
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .path(String.format("/staff-management/store/" + storeId + "/invite-staff/" + staffId))
                .method(HttpMethod.POST.name())
                .message("직원등록 성공")
                .data("")
                .build();
    }

    public ResponseDto createEmploymentContract(Auth auth, Long storeId, Long staffId,
                                                MultipartFile contractFile,
                                                ContractCreateRequestDto contractCreateRequestDto){
        return ResponseDto.builder().build();
    }
    @Transactional
    public ResponseDto updateEmploymentWorkSchedule(Auth owner, Long storeId, Long staffId, WeekWorkAddRequest weekWorkAddRequest){
//        ownerRepository.findByPhone(owner.getPhone()).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
//        storeRepository.findByOwner_AuthIdAndStoreId(owner.getAuthId(), storeId).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STORE));
        Employment employment = employmentRepository.findByStore_StoreIdAndStaff_AuthId(storeId, staffId).orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
        WeekWorkTime weekWorkTime = WeekWorkTime.addRequestOf(weekWorkAddRequest);
        weekWorkTime.setEmployment(employment);
        weekWorkTimeRepository.save(weekWorkTime);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.toString())
                .message("근무일정 추가")
                .data("")
                .build();
    }
}
