package com.idt.aiowebflux.dto;


public record LicenseDto(
        String adminId,
        String licenseKey,
        int term
) {

}
