package com.idt.aiowebflux.request;

public record LicenseRequest(
        String adminId,
        int term
) {
}
