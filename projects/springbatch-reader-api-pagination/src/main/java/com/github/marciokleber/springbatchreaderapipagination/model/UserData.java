package com.github.marciokleber.springbatchreaderapipagination.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String status;
}