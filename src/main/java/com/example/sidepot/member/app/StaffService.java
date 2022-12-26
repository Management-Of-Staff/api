package com.example.sidepot.member.app;

import com.example.sidepot.member.domain.staff.StaffRepository;
import com.example.sidepot.member.dto.MemberDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;

import com.example.sidepot.member.domain.staff.Staff;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StaffService implements UserDetailsService {

    private final StaffRepository staffRepository;
    private final MemberValidator memberValidator;

    public MemberDto.StaffDto register(MemberDto.StaffDto staffDto){
        memberValidator.checkStaffDuplicate(staffDto.getPhone());
        Staff staff = memberValidator.staffDtoToEntity(staffDto);
        staffRepository.save(staff);
        return MemberDto.StaffDto.from(staff);
    }

    public ResponseEntity<?> login(MemberDto.ReqMemberLoginDto dto) throws Exception{
        Staff staff = staffRepository.findByPhone(dto.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        return ResponseEntity.ok().body(MemberDto.ResMemberLoginDto.of(staff.getPhone(), staff.getName(),null,null));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByPhone(username).orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        return (UserDetails) staff;
    }
}
