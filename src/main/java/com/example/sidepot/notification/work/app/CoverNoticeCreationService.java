package com.example.sidepot.notification.work.app;

import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.employment.domain.EmploymentRepository;
import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.NoticeType;
import com.example.sidepot.notification.work.domain.ReceiverId;
import com.example.sidepot.notification.work.domain.StaffNotice;
import com.example.sidepot.work.domain.CoverManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CoverNoticeCreationService {
    private final EmploymentRepository employmentRepository;

    public List<StaffNotice> createRequestedNotice(List<CoverManager> coverManagerList) {
        List<StaffNotice> staffNoticeList = new ArrayList<>();
        for (CoverManager coverManager : coverManagerList) {
            List<Employment> employmentList = employmentRepository.findAllByStore_StoreId(coverManager.getStoreId().getStoreId());
            for (Employment employment : employmentList) {
                if(!(coverManager.getSenderId().getSenderId().equals(employment.getStaff().getMemberId())))
                staffNoticeList.add(new StaffNotice(
                        new CoverManagerId(coverManager.getId(), coverManager.getSenderId()),
                        new ReceiverId(employment.getStaff().getMemberId(), employment.getStaff().getUuid()),
                        NoticeType.REQUESTED));
            }
        }
        return staffNoticeList;
    }


    public StaffNotice createAcceptedNotice(CoverManagerId coverManagerId, ReceiverId receiverId) {
        //루트에 있어야 할 도메인 로직인데 일관성을 위해서 도메인 서비스로 추출했음
        return new StaffNotice(coverManagerId, receiverId, NoticeType.ACCEPTED);
    }
}
