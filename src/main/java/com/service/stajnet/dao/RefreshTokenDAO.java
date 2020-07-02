package com.service.stajnet.dao;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDAO {
    
    @NotBlank
    private String refreshToken;
    private String username;
}