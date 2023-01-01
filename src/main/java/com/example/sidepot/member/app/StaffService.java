package com.example.sidepot.member.app;

import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.member.dto.MemberDto;
import com.example.sidepot.member.dto.MemberDto.StaffDto;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.util.MemberValidator;
import com.example.sidepot.member.domain.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final MemberValidator memberValidator;

    @Transactional
    public StaffDto create(StaffDto staffDto){
        memberValidator.checkStaffDuplicate(staffDto.getPhone());
        Staff staff = memberValidator.staffDtoToEntity(staffDto);
        staffRepository.save(staff);
        return StaffDto.from(staff);
    }


    @Transactional(readOnly = true)
    public StaffDto read(Auth auth){
        Staff staff = staffRepository.findById(auth.getAuthId())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return StaffDto.from(staff);
    }

    @Transactional
    public Long update(MemberDto.MemberUpdateDto memberUpdateDto, Auth auth){
        Staff staff = staffRepository.findByPhone(auth.getPhone())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        memberUpdateDto.setPassword(memberValidator.encodePassword(memberUpdateDto.getPassword()));
        return staffRepository.save(staff.update(memberUpdateDto)).getAuthId();
    }

    @Transactional
    public void delete(Auth auth){
        staffRepository.deleteByAuthId(auth.getAuthId());
    }
}
