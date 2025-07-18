package com.idt.aiowebflux.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idt.aiowebflux.entity.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KnowledgeAccountPageDto {

    private Integer knowledgeId;
    private String accountId;
    private String adminId;
    private Role role;
    private String name;
    private String licenseKey;
    private LocalDate expiredDt;
}