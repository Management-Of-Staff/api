package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.attendance.domain.WorkerId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "work_manager")
@Entity
public class WorkManager {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StoreInfo storeInfo;

    @Embedded
    private WorkerId workerId;

    @OneToMany(mappedBy = "workManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkTime> workTimeList = new ArrayList<>();

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    public WorkManager(StoreInfo storeInfo, WorkerId workerId, List<WorkTime> workTimeList) {
        this.storeInfo = storeInfo;
        this.workerId = workerId;
        setWorkList(workTimeList);
        this.isDeleted = false;
    }

    public void delete(){
        this.isDeleted = true;
        this.getWorkTimeList().forEach(wt -> wt.delete());
    }

    private void setWorkList(List<WorkTime> workTimeList){
        this.workTimeList.addAll(
                workTimeList.stream()
                        .map(wt -> wt.setWorkManager(this))
                        .collect(Collectors.toList()));
    }


}
