package com.example.sidepot.work.app;

import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.global.security.LoginMember;

import com.example.sidepot.notification.work.domain.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.domain.WorkTimeRepository;
import com.example.sidepot.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CoverWorkNoticeCommandService {
    private final WorkTimeRepository workTimeRepository;
    private final CoverNoticeRepository coverNoticeRepository;

    @Transactional
    public void createCoverWorkRequestNotice(LoginMember sender, List<CreateCoverWorkReqDto> createCoverWorkReqDtoList) {
        //변동 근무에 해당되는 고정 근무를 패치를 통해 필요한 모든 것을 조회
        List<WorkTime> workTimeList
                = workTimeRepository.findAllById(createCoverWorkReqDtoList
                .stream().map(o -> o.getWorkTimeId()).collect(Collectors.toList()));

        //매장 기준으로 알림내용을 그룹핑
        Map<Store, List<WorkTime>> workTimeByStore
                = workTimeList.stream().collect(Collectors.groupingBy(o -> o.getStore()));
        Map<Long, List<CreateCoverWorkReqDto>> requestListEachStore
                = createCoverWorkReqDtoList.stream().collect(Collectors.groupingBy(dto -> dto.getStoreId()));

        //매장을 기준으로 알림 생성 -> TODO insert 배치 안쓰면 O(n^2) 쿼리 날라감
        List<CoverNotice> coverNoticeList = relateNoticeToStaffs(sender, workTimeByStore, requestListEachStore);

        // 알림 저장 -> TODO @OneToMany 단방향으로 부모 객체가 영속성을 관리 -> 너무 복잡
        coverNoticeRepository.saveAll(coverNoticeList);

        //푸시 알림 서비스 이벤트 호출
        /*코드*/

        //알림함 서비스 이벤트 호출
        /*코드*/
    }

    private List<CoverNotice> relateNoticeToStaffs(LoginMember sender, Map<Store, List<WorkTime>> workTimeByStore,
                                                   Map<Long, List<CreateCoverWorkReqDto>> requestListEachStore) {
        List<CoverNotice> coverNoticeList = new ArrayList<>();
        for (Store store : workTimeByStore.keySet()) {
            if (requestListEachStore.containsKey(store.getStoreId())) {
                CoverNotice noticeEachStore = createNoticeEachStore(requestListEachStore.get(store.getStoreId()), workTimeByStore.get(store));
                List<NoticeMember> noticeMember = createNoticeMember(store, noticeEachStore, sender);
                noticeEachStore.setNoticeMember(noticeMember);
                coverNoticeList.add(noticeEachStore);
            }
        }
        return coverNoticeList;
    }

    private CoverNotice createNoticeEachStore(List<CreateCoverWorkReqDto> requestListEachStore, List<WorkTime> workTimeList) {
        return new CoverNotice(requestListEachStore, workTimeList);
    }

    private List<NoticeMember> createNoticeMember(Store store, CoverNotice coverNotice, LoginMember sender) {
        List<NoticeMember> noticeMemberList = new ArrayList<>();
        for (Employment employment : store.getEmploymentList()) {
            if (!sender.getMemberId().equals(employment.getStaff().getMemberId())) {
                NoticeMember noticeMember = new NoticeMember(coverNotice, employment.getStaff(), false);
                noticeMemberList.add(noticeMember);
            }
        }
        return noticeMemberList;
    }
}
