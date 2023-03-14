package com.example.sidepot.work.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.*;
import com.example.sidepot.work.dao.EmploymentQuery;
import com.example.sidepot.work.dao.EmploymentWorkList;
import com.example.sidepot.work.domain.*;
import com.example.sidepot.work.dto.EmploymentAddDto.*;
import com.example.sidepot.work.dto.EmploymentReadDto.*;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import com.example.sidepot.work.dto.WorkTimeRequest.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmploymentService {
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;
    private final OwnerRepository ownerRepository;
    private final WeekWorkTimeRepository weekWorkTimeRepository;
    private final EmploymentQuery employmentQuery;

    @Transactional(readOnly = true)
    public ResponseDto readAllStaffByStoreId(LoginMember member, Long storeId){
        Store verifiedStore = storeRepository
                .findByOwnerAndStoreId(ownerRepository.findByMemberId(member.getMemberId()), storeId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        List<EmploymentWorkList> employmentWorkLists
                = employmentQuery.readAllEmployment(member.getMemberId(), verifiedStore.getStoreId());
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.GET.name())
                .message("매장 직원 목록 조회")
                .data(employmentWorkLists)
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto readEmployment(Member member, Long storeId, Long staffId){
        Store verifiedStore = storeRepository
                .findByOwnerAndStoreId(ownerRepository.findByMemberId(member.getMemberId()), storeId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        Employment employment = employmentRepository.findByStore_StoreIdAndStaff_MemberId(verifiedStore.getStoreId(), staffId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STAFF_IN_STORE));
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.GET.name())
                .message("매장 직원 조회")
                .data(ReadOneEmploymentResponse.of(employment))
                .build();
    }

    @Transactional
    public ResponseDto addStaffToStoreByStoreId(Long storeId, Long staffId){
        Store store = storeRepository.findById(storeId).orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        Staff staff = staffRepository.findById(staffId).orElseThrow(()-> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        employmentRepository.save(Employment.createEmployment(store, staff));
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message("직원등록 성공")
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateEmploymentWorkSchedule(LoginMember member, Long storeId, Long staffId, WeekWorkAddRequest weekWorkAddRequest){
        Employment employment = employmentRepository.findByStore_StoreIdAndStaff_MemberId(storeId, staffId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
        List<WeekWorkTime> weekWorkTimeList =
                weekWorkAddRequest.getDayOfWeekList().stream()
                    .map(day -> WeekWorkTime.createWeekWorkTime(employment, day,
                                                                weekWorkAddRequest.getStartTime(),
                                                                weekWorkAddRequest.getEndTime()))
                    .collect(Collectors.toList());
        weekWorkTimeRepository.saveAll(weekWorkTimeList);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.POST.name())
                .message("근무일정 추가")
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto deleteEmploymentWorkSchedule(LoginMember member, WeekWorkDeleteRequest weekWorkDeleteRequest) {
        employmentRepository.
                findByStore_StoreIdAndStaff_MemberId(weekWorkDeleteRequest.getStoreId(), weekWorkDeleteRequest.getStaffId())
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));

        weekWorkTimeRepository.delete(
                weekWorkTimeRepository.findById(weekWorkDeleteRequest.getWeekWorkTimeId())
                        .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_WORK_SCHEDULE)));
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.PUT.name())
                .message("근무 일정 삭제")
                .data("")
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseDto findStaffToInvite(Member member, FindStaffToInviteRequest findStaffToInviteRequest) {
        Staff staff = staffRepository.findByMemberPhoneNum(findStaffToInviteRequest.getPhoneNum())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpMethod.POST.name())
                .method("초대 직원 검색")
                .data(FindStaffToInviteResponse.of(staff))
                .build();
    }
    @Transactional
    public ResponseDto updateStoreStaffRankAndWage(LoginMember member, UpdateRankAndWageRequest updateRankAndWageRequest) {
        Employment employment = employmentRepository
                .findByStore_StoreIdAndStaff_MemberId(updateRankAndWageRequest.getStoreId(), updateRankAndWageRequest.getStaffId())
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
        employment.updateRankAndWage(updateRankAndWageRequest);
        employmentRepository.save(employment);
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpMethod.POST.name())
                .method("근무 정보 수정")
                .data("")
                .build();
    }
}
