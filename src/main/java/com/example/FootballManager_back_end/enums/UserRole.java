package com.example.FootballManager_back_end.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");
    String label;
}
