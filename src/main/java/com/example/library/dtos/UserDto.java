package com.example.library.dtos;

import com.example.library.models.UserType;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private UserType userType;
    private String address;
    private String city;
    private String country;
    private String password;
    private String confirmPassword;
}
