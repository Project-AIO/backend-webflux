package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectAccountRequest(

        @NotNull
        List<String> accountList
) {
}
