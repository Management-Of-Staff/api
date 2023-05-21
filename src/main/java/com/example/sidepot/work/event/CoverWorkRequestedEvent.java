package com.example.sidepot.work.event;

import com.example.sidepot.work.domain.CoverManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class CoverWorkRequestedEvent {

    private List<CoverManager> coverManagerList;
    private Set<Long> storeIds;

    public CoverWorkRequestedEvent(List<CoverManager> coverManagerList, Set<Long> storeIds) {
        this.coverManagerList = coverManagerList;
        this.storeIds =  storeIds;
    }
}
