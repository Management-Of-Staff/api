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
@Configuration
@RequiredArgsConstructor
public class CreateAttendanceBatchJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job createAttendanceJob() {

        Job createAttendanceJob = jobBuilderFactory.get("trendingMovieJob")
                .start(readWorkTimeStep())
                .build();
        return createAttendanceJob;
    }

    @Bean
    @JobScope
    public Step readWorkTimeStep() {
        return stepBuilderFactory.get("readWorkTimeStep")
                .<WorkTime, Attendance>chunk(10)
                .reader(workTimeReader())
                .processor(processor())
                .writer(dataWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter dataWriter() {
        return new JpaItemWriterBuilder<Attendance>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<WorkTime> workTimeReader() {
        LocalDate now = LocalDate.now();
        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("onDay", now);
        parameterValues.put("dayOfWeek", now.getDayOfWeek());

        String queryString = "SELECT wt " +
                "FROM WorkTime wt " +
                "LEFT JOIN CoverWork cw " +
                "    ON wt.workTimeId = cw.workTime.workTimeId " +
                "    AND cw.coverDateTime.coverDate = :onDay " +
                "WHERE wt.isDeleted = false " +
                "    AND wt.dayOfWeek = :dayOfWeek " +
                "    AND cw.workTime IS NULL ";

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
    public ItemProcessor<WorkTime, Attendance> processor(){
        return new ItemProcessor<WorkTime, Attendance>() {
            @Override
            public Attendance process(WorkTime workTime) {
                Attendance attendance = Attendance.newAttendance((WorkTime) workTime, LocalDate.now());
                return attendance;
            }
        };
    }
}
