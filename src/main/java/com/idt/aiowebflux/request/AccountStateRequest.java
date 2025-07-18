package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.Status;
import jakarta.validation.constraints.NotNull;

public record AccountStateRequest(
        @NotNull
        Status status
) {
}
