package com.myblog9.Myblog.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String Username;
    private String email;
    private String Password;
}
