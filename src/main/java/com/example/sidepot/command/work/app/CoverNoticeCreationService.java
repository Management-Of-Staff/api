package com.example.sidepot.command.work.app;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.employment.repository.EmploymentRepository;
import com.example.sidepot.command.work.domain.CoverManagerId;
import com.example.sidepot.command.notification.common.NoticeType;
import com.example.sidepot.command.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Deprecated
@RequiredArgsConstructor
@Service
public class CoverNoticeCreationService {
    private final EmploymentRepository employmentRepository;

    /**
     * 요청한 대타 알림 생성
     */
    public List<StaffCoverNoticeBox> createRequestedNotice(List<CoverManager> coverManagerList) {
        List<StaffCoverNoticeBox> staffCoverNoticeBoxList = new ArrayList<>();
        for (CoverManager coverManager : coverManagerList) {
            List<Employment> employmentList = findAllEmploymentByStore(coverManager);
            for (Employment employment : employmentList) {
                //addNewStaffNotice(staffCoverNoticeBoxList, coverManager, employment);
            }
        }
        return staffCoverNoticeBoxList;
    }

    /**
     * 수락한 대타 알림 생성
     */

    public StaffCoverNoticeBox createAcceptedNotice(CoverManagerId coverManagerId, Sender sender, Receiver receiver) {
        return new StaffCoverNoticeBox(coverManagerId, sender, receiver, NoticeType.ACCEPTED);
    }

//    private void addNewStaffNotice(List<StaffCoverNoticeBox> staffCoverNoticeBoxList, CoverManager coverManager, Employment employment) {
//        if(!(coverManager.getRequestedStaff().getId().equals(employment.getStaff().getMemberId())))
//        staffCoverNoticeBoxList.add(StaffCoverNoticeBox.newStaffNotice(coverManager, employment, NoticeType.REQUESTED));
//    }

    private List<Employment> findAllEmploymentByStore(CoverManager coverManager) {
        Optional<List<Employment>> employmentList
                = Optional.ofNullable(employmentRepository.findAllByStore_StoreId(coverManager.getStoreInfo().getStoreId()));
        return employmentList.orElse(Collections.emptyList());
    }

}
