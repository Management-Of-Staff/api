package com.example.sidepot.work.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dao.WorkReedQuery;
import com.example.sidepot.work.domain.Employment;
import com.example.sidepot.work.domain.EmploymentRepository;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.domain.WorkTimeRepository;
import com.example.sidepot.work.dto.WorkTimeRequest.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class WorkTimeCommandService {

    private final EmploymentRepository employmentRepository;
    private final WorkTimeRepository workTimeRepository;
    private final WorkReedQuery workReedQuery;

    @Transactional
    public void createEmploymentWorkSchedule(LoginMember member, Long employmentId,
                                                    WeekWorkAddRequest weekWorkAddRequest){
        Employment employment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));

        isScheduleOverlapping(employment.getStaff().getMemberId(), weekWorkAddRequest.getDayOfWeekList(),
                              weekWorkAddRequest.getStartTime(), weekWorkAddRequest.getEndTime());

        List<WorkTime> workTimeList = createWorkTimeList(weekWorkAddRequest, employment);
        workTimeRepository.saveAll(workTimeList);
    }

    @Transactional
    public void updateEmploymentWorkSchedule(LoginMember member, Long employmentId,
                                                    WeekWorkUpdateRequest weekWorkUpdateRequest){
        Employment employment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));

        isScheduleOverlapping(employmentId,
                              weekWorkUpdateRequest.getWeekWorkAddRequest().getDayOfWeekList(),
                              weekWorkUpdateRequest.getWeekWorkAddRequest().getStartTime(),
                              weekWorkUpdateRequest.getWeekWorkAddRequest().getEndTime());

        workTimeRepository.deleteAllById(weekWorkUpdateRequest.getWeekWorkIds());
        List<WorkTime> workTimeList = createWorkTimeList(weekWorkUpdateRequest.getWeekWorkAddRequest(), employment);
        workTimeRepository.saveAll(workTimeList);
    }

    @Transactional
    public void deleteEmploymentWorkSchedule(LoginMember member, Long employmentId,
                                                    WeekWorkDeleteRequest weekWorkDeleteRequest) {
        employmentRepository.findById(employmentId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
        workTimeRepository.deleteAll(
                workTimeRepository.findAllById(weekWorkDeleteRequest.getWeekWorkTimeIds()));
    }

    private List<WorkTime> createWorkTimeList(WeekWorkAddRequest weekWorkAddRequest,
                                              Employment employment) {
        List<WorkTime> workTimeList =
                weekWorkAddRequest.getDayOfWeekList().stream()
                        .map(day -> WorkTime.createWorkTime(employment, day,
                                                                weekWorkAddRequest.getStartTime(),
                                                                weekWorkAddRequest.getEndTime()))
                        .collect(Collectors.toList());
        return workTimeList;
    }

    private void isScheduleOverlapping(Long staffId, Set<DayOfWeek> days,
                                       LocalTime startTime, LocalTime endTime){
        List<WorkTime> workTimes =
                workReedQuery.readAllEmploymentInTime(staffId, days, startTime, endTime);
        if(!workTimes.isEmpty()){
            throw new Exception(ErrorCode.OVERLAP_WORK_SCHEDULE);
        }
    }
}
