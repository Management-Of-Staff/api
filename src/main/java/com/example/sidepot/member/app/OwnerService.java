package com.example.sidepot.member.app;

import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.dto.MemberDto.*;
import com.example.sidepot.member.error.ErrorCode;
import com.example.sidepot.member.error.Exception;
import com.example.sidepot.member.util.MemberValidator;
import com.example.sidepot.security.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class OwnerService {

    private final MemberValidator memberValidator;
    private final OwnerRepository ownerRepository;

    @Transactional
    public OwnerDto create(OwnerDto dto) {
        Owner owner = memberValidator.ownerDtoToEntity(dto);
        memberValidator.checkOwnerDuplicate(dto.getPhone());
        ownerRepository.save(owner);

        return OwnerDto.from(owner);
    }

    @Transactional(readOnly = true)
    public OwnerDto read(Auth auth){
        Owner owner = ownerRepository.findById(auth.getAuthId())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        return OwnerDto.from(owner);
    }

    @Transactional
    public Long update(MemberUpdateDto memberUpdateDto, Auth auth){
        Owner owner = ownerRepository.findByPhone(auth.getPhone())
                .orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));
        memberUpdateDto.setPassword(memberValidator.encodePassword(memberUpdateDto.getPassword()));
        return ownerRepository.save(owner.update(memberUpdateDto)).getAuthId();
    }

    @Transactional
    public void delete(Auth auth){
         ownerRepository.deleteByAuthId(auth.getAuthId());
    }
}

