package com.example.sidepot.schedule;

import com.example.sidepot.command.attendance.domain.Attendance;
import com.example.sidepot.command.attendance.domain.CoverAttendance;
import com.example.sidepot.command.work.domain.CoverManagerStatus;
import com.example.sidepot.command.work.domain.CoverWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
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
public class CreateCoverAttendanceJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job createCoverAttendanceJob(){

        Job createCoverAttendanceJob = jobBuilderFactory.get("createCoverAttendanceJob")
                .start(coverWorkStep())
                .build();
        return  createCoverAttendanceJob;
    }

    @Bean
    @JobScope
    public Step coverWorkStep() {
        return stepBuilderFactory.get("coverWorkStep")
                .<CoverWork, CoverAttendance>chunk(10)
                .reader(coverWorkReader())
                .processor(coverProcessor())
                .writer(coverWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<CoverWork> coverWorkReader() {
        LocalDate now = LocalDate.now();
        LocalDate test = LocalDate.of(2023, 05, 22);
        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("onDay", test);
        parameterValues.put("coverManagerStatus", CoverManagerStatus.ACCEPTED);


        String queryString = "SELECT cw " +
                "FROM CoverWork cw " +
                "JOIN FETCH cw.coverManager cm " +
                "WHERE cm.coverManagerStatus = :coverManagerStatus " +
                "    AND cw.coverDateTime.coverDate = :onDay " +
                " ORDER BY cw.coverWorkId ASC ";


        return new JpaPagingItemReaderBuilder<CoverWork>()
                .pageSize(10)
                .parameterValues(parameterValues)
                .queryString(queryString)
                .entityManagerFactory(entityManagerFactory)
                .name("coverWorkReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<CoverWork, CoverAttendance> coverProcessor(){
        return new ItemProcessor<CoverWork, CoverAttendance>() {
            @Override
            public CoverAttendance process(CoverWork coverWork) {
                CoverAttendance attendance = CoverAttendance.newCoverAttendance(coverWork, LocalDate.of(2023,05,22));//LocalDate.now());
                return attendance;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter coverWriter() {
        return new JpaItemWriterBuilder<Attendance>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
