package com.example.sidepot.member.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.BaseEntityRepository;
import com.example.sidepot.member.domain.owner.Owner;
import com.example.sidepot.member.domain.owner.OwnerRepository;
import com.example.sidepot.member.dto.MemberDto.*;
import com.example.sidepot.member.util.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerService implements UserDetailsService {

    private final MemberValidator memberValidator;
    private final OwnerRepository ownerRepository;

    @Transactional
    public OwnerDto register(OwnerDto dto) {
        Owner owner = memberValidator.ownerDtoToEntity(dto);
        memberValidator.checkOwnerDuplicate(dto.getPhone());
        ownerRepository.save(owner);

        return OwnerDto.from(owner);
    }

    public ResponseEntity<?> login(ReqMemberLoginDto dto) {
        Owner owner = ownerRepository.findByPhone(dto.getPhone())
                .orElseThrow(() -> new Exception(ErrorCode.MEMBER_NOT_FOUND));


        return ResponseEntity.ok().body(ResMemberLoginDto.of(owner.getPhone(), owner.getName(),null,null));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findByPhone(username).orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));
        return owner;
    }
}

