package com.example.sidepot.work.domain;

import com.example.sidepot.member.domain.Employment;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.dto.WorkTimeRequest;
import com.example.sidepot.store.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "week_work_time")
@NoArgsConstructor
public class WeekWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_work_time_id")
    private Long weekWorkTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @ElementCollection
    @CollectionTable(name = "day_of_week",
    joinColumns = @JoinColumn(name = "week_work_time"))
    @Column(name = "day")
    private Set<DayOfWeek> day = new HashSet<>();

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    @CreationTimestamp
    @Column(name = "create_time",
            updatable = false,
            nullable = false)
    private LocalDateTime createTime;
    @Builder
    public WeekWorkTime(Staff staff, Store store, Employment employment, Set<DayOfWeek> day, LocalDate startDate,
                        LocalDate endDate, LocalTime startTime, LocalTime endTime, LocalDateTime createTime) {
        this.staff = staff;
        this.store = store;
        this.employment = employment;
        this.day = day;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
    }

    public void setEmployment(Employment employment){
        this.employment = employment;
    }

    public static WeekWorkTime addRequestOf(WorkTimeRequest.WeekWorkAddRequest weekWorkAddRequest){
        return WeekWorkTime.builder()
                .day(weekWorkAddRequest.getDayOfWeekList())
                .startDate(weekWorkAddRequest.getStartDate())
                .endDate(weekWorkAddRequest.getEndDate())
                .startTime(weekWorkAddRequest.getStartTime())
                .endTime(weekWorkAddRequest.getEndTime())
                .build();
    }

    /**
     * 근무 요일 조회
     * ex) '월, 화, 수'
     */
    public String getWorkDays() {
        // 예외 예정
        if(day == null || day.isEmpty()) {
            return "";
        }
        return day.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }
}
