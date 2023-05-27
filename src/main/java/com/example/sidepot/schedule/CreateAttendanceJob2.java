package com.example.sidepot.schedule;

import com.example.sidepot.command.attendance.domain.Attendance;
import com.example.sidepot.command.work.domain.WorkTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CreateAttendanceJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job createAttendanceJob() {

        Job createAttendanceJob = jobBuilderFactory.get("createAttendanceJob")
                .start(workTimeStep())
                .build();
        return createAttendanceJob;
    }

    @Bean
    @JobScope
    public Step workTimeStep() {
        return stepBuilderFactory.get("workTimeStep")
                .<WorkTime, Attendance>chunk(10)
                .reader(workTimeReader())
                .processor(workProcessor())
                .writer(workWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<WorkTime> workTimeReader() {
        LocalDate now = LocalDate.now();//.minusDays(1);
        LocalDate test = LocalDate.of(2023, 05, 22);
        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("onDay", test);
        parameterValues.put("dayOfWeek", test.getDayOfWeek());

        String queryString = "SELECT wt " +
                "FROM WorkTime wt " +
                "LEFT JOIN CoverWork cw " +
                "    ON wt.workTimeId = cw.workTime.workTimeId " +
                "    AND cw.coverDateTime.coverDate = :onDay " +
                "WHERE wt.isDeleted = false " +
                "    AND wt.dayOfWeek = :dayOfWeek " +
                "    AND cw.workTime IS NULL " +
                " ORDER BY wt.workTimeId ASC ";

        return new JpaPagingItemReaderBuilder<WorkTime>()
                .pageSize(10)
                .parameterValues(parameterValues)
                .queryString(queryString)
                .entityManagerFactory(entityManagerFactory)
                .name("workTimeReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<WorkTime, Attendance> workProcessor(){
        return new ItemProcessor<WorkTime, Attendance>() {
            @Override
            public Attendance process(WorkTime workTime) {
                Attendance attendance = Attendance.newAttendance(workTime, LocalDate.now());
                return attendance;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter workWriter() {
        return new JpaItemWriterBuilder<Attendance>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
