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
public class AttendanceScheduleConfig {

    private final JobLauncher jobLauncher;
    private final CreateAttendanceJob2 createAttendanceJob;
    private final CreateCoverAttendanceJob2 createCoverAttendanceJob2;

    //@Scheduled(cron = "0 0 0 * * *") // 매일 00시에 실행
    @Scheduled(initialDelay = 15000, fixedDelay = Long.MAX_VALUE)
    public void setAttendanceJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(createAttendanceJob.createAttendanceJob(), jobParameters);
    }

    //@Scheduled(cron = "0 0 0 * * *")
    @Scheduled(initialDelay = 15000, fixedDelay = Long.MAX_VALUE)
    public void setCoverAttendanceJon() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(createCoverAttendanceJob2.createCoverAttendanceJob(), jobParameters);
    }
}