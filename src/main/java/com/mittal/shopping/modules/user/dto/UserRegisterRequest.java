package com.mittal.shopping.modules.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private String name;
    private String email;
    private String password;

}
