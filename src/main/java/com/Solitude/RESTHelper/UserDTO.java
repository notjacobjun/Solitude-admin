package com.Solitude.RESTHelper;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String email;
    private String displayName;
}
