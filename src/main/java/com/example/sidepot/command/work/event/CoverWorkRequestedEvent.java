package com.example.sidepot.command.work.event;

import com.example.sidepot.command.work.domain.CoverManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class CoverWorkRequestedEvent {

    private List<CoverManager> coverManagerList;

    public CoverWorkRequestedEvent(List<CoverManager> coverManagerList) {
        this.coverManagerList = coverManagerList;
    }
}
