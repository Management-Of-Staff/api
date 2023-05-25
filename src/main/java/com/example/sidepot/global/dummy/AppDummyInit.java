package com.example.sidepot.global.dummy;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.employment.domain.EmploymentRepository;
import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.OwnerRepository;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import com.example.sidepot.command.work.repository.WorkTimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppDummyInit extends AppDummyObject {

    @Bean
    CommandLineRunner initDB(StaffRepository staffRepository, OwnerRepository ownerRepository,
                             StoreRepository storeRepository, WorkTimeRepository workTimeRepository,
                             CoverWorkRepository coverWorkRepository, EmploymentRepository employmentRepository) {
        return (args -> {
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
            WorkTime workTime1 = workTimeRepository.save(
                    newWorkTime(store1, staff1, LocalTime.of(9, 0, 0),
                            LocalTime.of(15, 0, 0), DayOfWeek.MONDAY));
            WorkTime workTime2 = workTimeRepository.save(
                    newWorkTime(store1, staff1, LocalTime.of(9, 0, 0),
                            LocalTime.of(15, 0, 0), DayOfWeek.WEDNESDAY));
            WorkTime workTime3 = workTimeRepository.save(
                    newWorkTime(store2, staff1, LocalTime.of(11, 0, 0),
                            LocalTime.of(20, 0, 0), DayOfWeek.SUNDAY));
            WorkTime workTime4 = workTimeRepository.save(
                    newWorkTime(store2, staff1, LocalTime.of(11, 0, 0),
                            LocalTime.of(20, 0, 0), DayOfWeek.SATURDAY));

            // 장수현의 고정 근무 일정
            WorkTime workTime5 = workTimeRepository.save(
                    newWorkTime(store1, staff2, LocalTime.of(9, 0, 0),
                            LocalTime.of(15, 0, 0), DayOfWeek.SUNDAY));
            WorkTime workTime6 = workTimeRepository.save(
                    newWorkTime(store1, staff2, LocalTime.of(9, 0, 0),
                            LocalTime.of(15, 0, 0), DayOfWeek.SATURDAY));
            WorkTime workTime7 = workTimeRepository.save(
                    newWorkTime(store2, staff2, LocalTime.of(11, 0, 0),
                            LocalTime.of(20, 0, 0), DayOfWeek.MONDAY));
            WorkTime workTime8 = workTimeRepository.save(
                    newWorkTime(store2, staff2, LocalTime.of(11, 0, 0),
                            LocalTime.of(20, 0, 0), DayOfWeek.FRIDAY));

//
            //이호섭의 고정 근무 일정
            WorkTime workTime9 = workTimeRepository.save(
                    newWorkTime(store1, staff4, LocalTime.of(11, 0, 0),
                            LocalTime.of(18, 0, 0), DayOfWeek.FRIDAY));

            WorkTime workTime10 = workTimeRepository.save(
                    newWorkTime(store1, staff4, LocalTime.of(11, 0, 0),
                            LocalTime.of(18, 0, 0), DayOfWeek.SATURDAY));

            WorkTime workTime11 = workTimeRepository.save(
                    newWorkTime(store2, staff4, LocalTime.of(11, 0, 0),
                            LocalTime.of(20, 0, 0), DayOfWeek.TUESDAY));

            WorkTime workTime12 = workTimeRepository.save(
                    newWorkTime(store2, staff4, LocalTime.of(11, 0, 0),
                            LocalTime.of(20, 0, 0), DayOfWeek.THURSDAY));

            List<WorkTime> batchWorktimeList = new ArrayList<>();
            for(int i = 0 ; i<100 ; i++){
                batchWorktimeList.add(newWorkTime(store1, staff1, null,null , DayOfWeek.WEDNESDAY));
            }
            workTimeRepository.saveAll(batchWorktimeList);
        });


    }
}


