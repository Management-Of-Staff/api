package com.example.sidepot.notification.work.app;

import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.employment.domain.EmploymentRepository;
import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.NoticeType;
import com.example.sidepot.notification.work.domain.Receiver;
import com.example.sidepot.notification.work.domain.StaffNotice;
import com.example.sidepot.work.domain.CoverManager;
import com.example.sidepot.notification.work.domain.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CoverNoticeCreationService {
    private final EmploymentRepository employmentRepository;

    /**
     * 요청한 대타 알림 생성
     */
    public List<StaffNotice> createRequestedNotice(List<CoverManager> coverManagerList) {
        List<StaffNotice> staffNoticeList = new ArrayList<>();
        for (CoverManager coverManager : coverManagerList) {
            List<Employment> employmentList = findAllEmploymentByStore(coverManager);
            for (Employment employment : employmentList) {
                addNewStaffNotice(staffNoticeList, coverManager, employment);
            }
        }
        return staffNoticeList;
    }

    /**
     * 수락한 대타 알림 생성
     */

    public StaffNotice createAcceptedNotice(CoverManagerId coverManagerId, Sender sender, Receiver receiver) {
        return new StaffNotice(coverManagerId, sender, receiver, NoticeType.ACCEPTED);
    }

    private void addNewStaffNotice(List<StaffNotice> staffNoticeList, CoverManager coverManager, Employment employment) {
        if(!(coverManager.getRequestedStaff().getId().equals(employment.getStaff().getMemberId())))
        staffNoticeList.add(StaffNotice.newStaffNotice(coverManager, employment, NoticeType.REQUESTED));
    }

    private List<Employment> findAllEmploymentByStore(CoverManager coverManager) {
        Optional<List<Employment>> employmentList
                = Optional.ofNullable(employmentRepository.findAllByStore_StoreId(coverManager.getStoreInfo().getStoreId()));
        return employmentList.orElse(Collections.emptyList());
    }

}
