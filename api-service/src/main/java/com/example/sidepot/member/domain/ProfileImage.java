package com.example.sidepot.member.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ProfileImage {

    @Column(name = "profile_save_path", nullable = false)
    private String profileSavePath;

    @Column(name = "profile_origin_name", nullable = false)
    private String  profileOriginName;

    public ProfileImage() {}

    public ProfileImage(String profileSavePath, String profileOriginName) {
        this.profileSavePath = profileSavePath;
        this.profileOriginName = profileOriginName;
    }
}
