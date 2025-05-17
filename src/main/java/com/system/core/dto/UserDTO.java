package com.system.core.dto;

import com.system.core.entity.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}