package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Owner;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {
    @Getter
    @ApiModel(value = "OwnerDto")
    public static class OwnerDto{
        private String name;
        private String phone;
        private String password;

        public OwnerDto(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public static OwnerDto from(Owner owner){
            return new OwnerDto(owner.getName(), owner.getPhone());

        }
    }

    public static class StaffDto{
        private String name;
        private String phone;
        private String password;
    }

    public static class StoreDto{
        private String business_num;
    }
}
