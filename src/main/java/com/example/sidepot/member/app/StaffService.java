package com.example.sidepot.member.app;

import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.member.dto.MemberDto.StaffDto;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final MemberValidator memberValidator;

    public StaffDto register(StaffDto staffDto){
        memberValidator.checkStaffDuplicate(staffDto.getPhone());
        Staff staff = memberValidator.staffDtoToEntity(staffDto);
        staffRepository.save(staff);
        return StaffDto.from(staff);
    }
}
