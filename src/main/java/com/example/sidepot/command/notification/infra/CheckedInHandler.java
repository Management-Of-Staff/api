package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.notification.app.FirebaseMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckedInHandler {
    private final FirebaseMessageService firebaseMessageService;
}
