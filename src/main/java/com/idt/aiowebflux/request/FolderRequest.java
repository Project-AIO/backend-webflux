package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;


public record FolderRequest(
        @NotNull
        String folderName
) {

}
