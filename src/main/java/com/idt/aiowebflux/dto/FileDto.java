package com.idt.aiowebflux.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    @NotNull
    MultipartFile file;
    @Size(min = 1, max = 1000)
    @NotNull
    int startPage;
    @Size(min = 1, max = 1000)
    @NotNull
    int endPage;

    public static FileDto from(final MultipartFile file, final Integer startPage, final Integer endPage) {
        return new FileDto(file, startPage, endPage);
    }
}
