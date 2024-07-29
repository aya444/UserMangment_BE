package com.example.usermanagement.model.entity;

import com.example.usermanagement.model.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private String mobileNumber;

    public static User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .build();
    }
}