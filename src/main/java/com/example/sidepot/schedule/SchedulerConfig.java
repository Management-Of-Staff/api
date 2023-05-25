package com.example.sidepot.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {


    private final JobLauncher jobLauncher;
    private final CreateAttendanceBatchJob createAttendanceBatchJob;

    //@Scheduled(cron = "0 0 0 * * *") // 매일 00시에 실행
    @Scheduled(fixedDelay = 5000)
    public void runUpdateDataJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(createAttendanceBatchJob.createAttendanceJob(), jobParameters);
    }
}