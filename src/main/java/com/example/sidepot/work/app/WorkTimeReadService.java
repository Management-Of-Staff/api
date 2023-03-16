package com.example.sidepot.work.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.dao.StaffWork;
import com.example.sidepot.work.dao.StaffWorkOnDay;
import com.example.sidepot.work.dao.WorkReedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WorkTimeReadService {
    private final WorkReedQuery workReedQuery;

    public Map<List<Serializable>, List<StaffWork>> readAllEmployment(Long memberId, Long storeId) {
        //로그인한 사장이 진짜 매장을 가지고 있는지 검증 해야함 -> 할 필요가 없나?
        //DB 에서 하면 필요 없는 쿼리가 많이 날라감
        //서비스에서 면?
        return workReedQuery.readAllEmployment(storeId);
    }

    public ResponseDto readAllStaffOnDay(LoginMember member, Long storeId, LocalDateTime onDay){
        int weekNumber = onDay.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        List<StaffWorkOnDay> staffWorkOnDay
                = workReedQuery.readAllEmploymentOnDay(member.getMemberId(), storeId, onDay.getDayOfWeek());
        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.GET.name())
                .message("매장 직원 목록 조회")
                .data(staffWorkOnDay)
                .build();
    }
}
