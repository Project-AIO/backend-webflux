package com.idt.aiowebflux.request;

import com.idt.aiowebflux.request.interace.DictionaryData;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SynonymFileData(
        @NotNull
        @Size(min = 1, max = 25)
        String source,
        @NotNull
        @Size(min = 1, max = 25)
        String match
) implements DictionaryData {
}
