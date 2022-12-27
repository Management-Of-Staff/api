package com.example.sidepot.member.app;

import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.OwnerRepository;
import com.example.sidepot.member.dto.MemberDto.*;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OwnerService {

    private final MemberValidator memberValidator;
    private final OwnerRepository ownerRepository;

    @Transactional
    public OwnerDto register(OwnerDto dto) {
        Owner owner = memberValidator.ownerDtoToEntity(dto);
        memberValidator.checkOwnerDuplicate(dto.getPhone());
        ownerRepository.save(owner);

        return OwnerDto.from(owner);
    }

}

