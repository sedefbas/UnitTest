package com.sedefbas.learnTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String LastName;
    private String phoneNumber;
    private String identificationNumber;
}
