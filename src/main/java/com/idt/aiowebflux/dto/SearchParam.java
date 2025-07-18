package com.idt.aiowebflux.dto;

import java.time.LocalDate;

public record SearchParam(
        String keyword,
        LocalDate from,
        LocalDate to
) {


}
