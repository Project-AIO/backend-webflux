package com.idt.aiowebflux.request;

import com.idt.aiowebflux.dto.SourceMatchDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DictionaryRequests(
        @NotNull
        List<SourceMatchDto> sourceMatchList
) {
}
