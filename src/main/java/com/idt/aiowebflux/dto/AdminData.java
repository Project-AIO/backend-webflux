package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;


public record AdminData(
        String adminId,
        String licenseKey
) {
    @QueryProjection
    public AdminData {

    }
}
