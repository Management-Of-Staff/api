package com.example.sidepot.work.app;

import com.example.sidepot.global.dummy.AppDummyObject;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.notification.work.domain.NoticeMemberRepository;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.domain.WorkTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CoverWorkNoticeCommandServiceTest extends AppDummyObject {

    @InjectMocks
    private CoverWorkNoticeCommandService noticeService;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private NoticeMemberRepository noticeMemberRepository;
    @Mock
    private WorkTimeRepository workTimeRepository;
    @Mock
    private StoreRepository storeRepository;


    @Test
    void 알림_생성_테스트() throws Exception{


    }

}