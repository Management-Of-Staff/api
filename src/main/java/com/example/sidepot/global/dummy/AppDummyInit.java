package com.example.sidepot.global.dummy;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.employment.domain.EmploymentRepository;
import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.OwnerRepository;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.repository.CoverManagerRepository;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import com.example.sidepot.command.work.repository.WorkManagerRepository;
import com.example.sidepot.command.work.repository.WorkTimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AppDummyInit extends AppDummyObject {

    @Bean
    CommandLineRunner initDB(StaffRepository staffRepository, OwnerRepository ownerRepository,
                             StoreRepository storeRepository, WorkManagerRepository workManagerRepository,
                             CoverManagerRepository coverManagerRepository, EmploymentRepository employmentRepository) {
        return args -> {
            Staff staff1 = staffRepository.save(newStaff("이지윤", "01000000001"));
            Staff staff2 = staffRepository.save(newStaff("장수현", "01000000002"));
            Staff staff3 = staffRepository.save(newStaff("김주현", "01000000003"));
            Staff staff4 = staffRepository.save(newStaff("이호섭", "01000000004"));
            Staff staff5 = staffRepository.save(newStaff("권희경", "01000000005"));
            Staff staff6 = staffRepository.save(newStaff("심규환", "01000000006"));

            Owner owner1 = ownerRepository.save(newOwner("김덕협", "1588-0101"));
            Owner owner2 = ownerRepository.save(newOwner("여춘팔", "1588-2424"));

            Store store1 = storeRepository.save(newStore("메가커피 천호점", owner1));
            Store store2 = storeRepository.save(newStore("스타벅스 당곡사거리점", owner2));

            // 알바 2개 이상 가진
            Employment employment1 = employmentRepository.save(newEmployment(store1, staff1));
            Employment employment2 = employmentRepository.save(newEmployment(store1, staff2));
            Employment employment9 = employmentRepository.save(newEmployment(store1, staff4));
            Employment employment3 = employmentRepository.save(newEmployment(store2, staff1));
            Employment employment4 = employmentRepository.save(newEmployment(store2, staff2));
            Employment employment10 = employmentRepository.save(newEmployment(store2, staff4));


            //그외
            Employment employment5 = employmentRepository.save(newEmployment(store1, staff3));
            Employment employment7 = employmentRepository.save(newEmployment(store1, staff5));
            Employment employment8 = employmentRepository.save(newEmployment(store1, staff6));



            // 이지윤의 고정 근무 일정
            //1번
            WorkTime workTime1 = newWorkTime(store1, staff1, LocalTime.of(9, 0, 0),
                    LocalTime.of(15, 0, 0), DayOfWeek.MONDAY);
            WorkTime workTime2 = newWorkTime(store1, staff1, LocalTime.of(9, 0, 0),
                    LocalTime.of(15, 0, 0), DayOfWeek.TUESDAY);

            workManagerRepository.save(newWorkManager(store1, staff1, Arrays.asList(workTime1, workTime2)));

            //2번
            WorkTime workTime3= newWorkTime(store1, staff1, LocalTime.of(11, 0, 0),
                    LocalTime.of(20, 0, 0), DayOfWeek.SATURDAY);
            WorkTime workTime4= newWorkTime(store1, staff1, LocalTime.of(11, 0, 0),
                    LocalTime.of(20, 0, 0), DayOfWeek.SUNDAY);

            workManagerRepository.save(newWorkManager(store1, staff1, Arrays.asList(workTime3, workTime4)));


            // 장수현의 고정 근무 일정
            //3번
            WorkTime workTime5 = newWorkTime(store1, staff2, LocalTime.of(9, 0, 0),
                    LocalTime.of(15, 0, 0), DayOfWeek.TUESDAY);

            WorkTime workTime6 = newWorkTime(store1, staff2, LocalTime.of(9, 0, 0),
                    LocalTime.of(15, 0, 0), DayOfWeek.WEDNESDAY);

            workManagerRepository.save(newWorkManager(store1, staff2, Arrays.asList(workTime5, workTime6)));

            //4번
            WorkTime workTime7 = newWorkTime(store1, staff2, LocalTime.of(11, 0, 0),
                    LocalTime.of(20, 0, 0), DayOfWeek.SATURDAY);

            WorkTime workTime8 = newWorkTime(store1, staff2, LocalTime.of(11, 0, 0),
                    LocalTime.of(20, 0, 0), DayOfWeek.SUNDAY);

            workManagerRepository.save(newWorkManager(store1, staff2, Arrays.asList(workTime7, workTime8)));

            //장수현 staff2이 workTime1번 근무에 대한 5월 22일 대타 근무를 이지윤staff1에게 요청하고 수락하였음
            //알림은 생략
            CoverWork coverWork1 = newCoverWork(staff2, staff2, LocalDate.of(2023, 05, 22), workTime1);
            coverManagerRepository.save(newCoverManager(store1, staff2, staff1, List.of(coverWork1)));

        };



    }
}


