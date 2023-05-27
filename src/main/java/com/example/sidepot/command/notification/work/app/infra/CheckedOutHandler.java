package com.example.sidepot.command.notification.work.app.infra;

import com.example.sidepot.command.notification.firebase.FirebaseMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckedOutHandler {
    private final FirebaseMessageService firebaseMessageService;
}
