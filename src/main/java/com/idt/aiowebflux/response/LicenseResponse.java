package com.idt.aiowebflux.response;


public record LicenseResponse(
        String adminId,
        String licenseKey,
        int term
) {

}
