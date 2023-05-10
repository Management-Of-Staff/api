package com.example.sidepot.work.app;


import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.domain.*;
import com.example.sidepot.work.dto.CoverWorkRequestDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CoverWorkCommandService {

    private final WorkTimeRepository workTimeRepository;
    private final CoverWorkRepository coverWorkRepository;
    private final CoverWorkNoticeRepository coverWorkNoticeRepository;

    @Transactional
    public void createCoverWorkRequest(LoginMember member, List<CreateCoverWorkReqDto> createCoverWorkReqDtoList) {
        // 변동근무와 고정근무를 맵핑하기 위한 고정 근무를 조회
        List<Long> workTimeIds = createCoverWorkReqDtoList.stream().map(c -> c.getWorkTimeId()).collect(Collectors.toList());
        List<WorkTime> workTimeListPs = workTimeRepository.findAllById(workTimeIds);

        // 각 매장에 대한 변동 근무 알림을 생성하기 위해 매장 ID로 매칭 후 알림과 변동 근무 생성(트랜잭션 OK)
        // 이벤트 퍼블리셔 패턴으로 알림서비스랑 분할하면 JPA 맵핑 걸려있어서 트랙잭션 죽음 -> TODO 서비스 분할 시도
        List<CoverWork> coverWorkList = new ArrayList<>();
        createCoverWorkReqDtoList.stream()
                .collect(Collectors.groupingBy(o -> o.getStoreId()))
                .forEach((storeId, value) -> {
                    CoverWorkNotice coverWorkNotice = createCoverWorkNotification(value.get(0));
                    for (CreateCoverWorkReqDto createCoverWorkReqDto : value) {
                        for (WorkTime workTime : workTimeListPs) {
                            if (createCoverWorkReqDto.getWorkTimeId().longValue() == workTime.getWorkTimeId().longValue()) {
                                CoverWork coverWork = new CoverWork(createCoverWorkReqDto, workTime, coverWorkNotice, member);
                                coverWorkList.add(coverWork);
                            }
                        }
                    }
                });
        coverWorkRepository.saveAll(coverWorkList);
    }

    @Transactional
    public CoverWorkNotice createCoverWorkNotification(CreateCoverWorkReqDto createCoverWorkReqDto) {
        return coverWorkNoticeRepository.save(new CoverWorkNotice(createCoverWorkReqDto));
    }

    @Transactional
    public void acceptCoverWorkRequest(LoginMember member, List<AcceptCoverWorkReqDto> acceptCoverWorkReqDtoList) {
        List<CoverWork> coverWorkPsList = coverWorkRepository.findAllById(acceptCoverWorkReqDtoList.stream()
                .map(o -> o.getCoverWorkTimeId()).collect(Collectors.toList()));

        coverWorkPsList.forEach(o -> o.coverWorkAccepted(member));
        coverWorkRepository.saveAll(coverWorkPsList);
    }

    public void rejectCoverWorkRequest(LoginMember member /*거절이유*/) {
        //거절 이유 테이블 조회
        //거절 이유 테이블 생성
    }
}
