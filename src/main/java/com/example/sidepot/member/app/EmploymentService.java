package com.example.sidepot.member.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.*;
import com.example.sidepot.member.dto.ContractCreateDto.*;
import com.example.sidepot.member.dto.EmploymentReadResponseDto.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmploymentService {
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;


    @Transactional(readOnly = true)
    public ResponseDto readAllStaffByStoreId(Auth auth, Long storeId){
        Store verifiedStore= storeRepository.findByOwner_AuthIdAndStoreId(auth.getAuthId(),storeId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        List<Employment> employmentList = employmentRepository.findAllByStore_StoreId(verifiedStore.getStoreId());
        List<ReadEmploymentListResponseDto> employmentListResponseDtoList
                = employmentList.stream().map(ReadEmploymentListResponseDto::of).collect(Collectors.toList());
        return ResponseDto.builder()
                .path(String.format("/staff-management/" + storeId))
                .method("GET")
                .statusCode(200)
                .message("매장 직원 목록 조회")
                .data(employmentListResponseDtoList)

                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto readEmployment(Auth auth, Long storeId, Long staffId){
        Store verifiedStore = storeRepository.findByOwner_AuthIdAndStoreId(auth.getAuthId(),storeId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        Employment employment = employmentRepository.findByStore_StoreIdAndStaff_AuthId(verifiedStore.getStoreId(), staffId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STAFF_IN_STORE));
        return ResponseDto.builder()
                .path(String.format("/staff-management/store/" + storeId + "/staff/" + staffId))
                .method("GET")
                .statusCode(200)
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
                .path(String.format("/staff-management/store/" + storeId + "/invite-staff/" + staffId))
                .method("POST")
                .message("직원등록 성공")
                .data("")
                .build();
    }

    public ResponseDto createEmploymentContract(Auth auth, Long StoreId, Long StaffId,
                                                MultipartFile contractFile,
                                                ContractCreateRequestDto contractCreateRequestDto){
        return ResponseDto.builder().build();
    }
}
