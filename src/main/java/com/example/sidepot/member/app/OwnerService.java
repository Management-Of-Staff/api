package com.example.sidepot.member.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.dto.MemberDto.MemberUpdateDto;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class OwnerService {

    private final MemberValidator memberValidator;
    private final OwnerRepository ownerRepository;

    @Transactional
    public OwnerDto registerOwner(OwnerDto dto) {
        Owner owner = memberValidator.ownerDtoToEntity(dto);
        memberValidator.checkOwnerDuplicate(dto.getPhone());
        ownerRepository.save(owner);

        return OwnerDto.from(owner);
    }

    @Transactional(readOnly = true)
    public OwnerDto readOwnerInfo(Auth auth){
        Owner owner = ownerRepository.findById(auth.getAuthId())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return OwnerDto.from(owner);
    }

    @Transactional
    public Long updateOwnerInfo(MemberUpdateDto memberUpdateDto, Auth auth){
        Owner owner = ownerRepository.findByPhone(auth.getPhone())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));
        memberUpdateDto.setPassword(memberValidator.encodePassword(memberUpdateDto.getPassword()));
        return ownerRepository.save(owner.updateMemberInfo(memberUpdateDto)).getAuthId();
    }

    @Transactional
    public void deleteOwner(Auth auth){
         ownerRepository.deleteByAuthId(auth.getAuthId());
    }
}

