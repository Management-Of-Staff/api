package com.example.sidepot.member.app;

import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.dto.MemberDto;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberValidator memberValidator;
    private final OwnerRepository memberRepository;

    @Transactional
    public OwnerDto register(OwnerDto ownerDto){
        memberValidator.checkMemberDuplicate(ownerDto.getPhone());
        Owner owner = memberValidator.ownerDtoToEntity(ownerDto);
        memberRepository.save(owner);
        return OwnerDto.from(owner);
    }
}
