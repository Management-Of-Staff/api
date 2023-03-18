package com.example.sidepot.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dao.StaffWork;
import com.example.sidepot.work.dao.StaffWorkOnDay;
import com.example.sidepot.work.dao.WorkReedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WorkTimeReadService {
    private final WorkReedQuery workReedQuery;

    public Map<List<Serializable>, List<StaffWork>> readAllEmployment(Long memberId, Long storeId) {
        return workReedQuery.readAllEmployment(storeId);
    }

    public List<StaffWorkOnDay> readAllEmploymentOnDay(LoginMember member, Long storeId, LocalDate onDay){
        int weekNumber = onDay.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        List<StaffWorkOnDay> staffWorkOnDay
                = workReedQuery.readAllEmploymentOnDay(member.getMemberId(), storeId, onDay.getDayOfWeek());
        return staffWorkOnDay;
    }
}
